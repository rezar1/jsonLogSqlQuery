package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.FileBlockStore.WriteBuffer;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.LeafFileBlockStore.LeafWriteBuffer;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月17日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class BplusTree<K extends Comparable<K> & DataSizeCountable, V extends DataSizeCountable>
		implements Iterable<V> {

	// private static Logger log = Logger.getLogger(BplusTree.class);

	@SuppressWarnings("rawtypes")
	private OldValueRemoveListener<BplusNode> onRemoveListener = null;

	/** 根节点 */
	// protected BplusNode<K, V> root;
	protected int rootId;

	/** 阶数，M值 */
	protected int order;

	/** 叶子节点的链表头 */
	// protected BplusNode<K, V> head;
	protected int headId;

	/** 树高 */
	protected int height = 0;

	public BplusNode<K, V> getHead() {
		return this.getNode(this.headId);
	}

	public void setHead(BplusNode<K, V> head) {
		this.headId = head.id;
	}

	public BplusNode<K, V> getRoot() {
		return this.getNode(this.rootId);
	}

	public void setRoot(BplusNode<K, V> root) {
		// this.root = root;
		this.rootId = root.id;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getHeight() {
		return height;
	}

	public V get(K key) {
		return this.getRoot().get(key);
	}

	public V remove(K key) {
		return this.getRoot().remove(key, this);
	}

	public void insertOrUpdate(K key, V value) {
		this.getRoot().insertOrUpdate(key, value, this);
	}

	private int blockSize = 1024;

	/**
	 * Bitset with id of free blocks to reuse
	 */
	private SimpleBitSet freeBlocks; // META-DATA: bitset of free blocks in

	/**
	 * Read cache for internal nodes
	 */
	@SuppressWarnings("rawtypes")
	private IntLinkedHashMap<BplusNode> cacheInternalNodes;

	/**
	 * Read cache for leaf nodes
	 */
	@SuppressWarnings("rawtypes")
	private IntLinkedHashMap<BplusNode> cacheLeafNodes;

	/**
	 * File for storage
	 */
	private final File fileStorage;

	/**
	 * Storage Object
	 */
	private final FileBlockStore storage;

	private final LeafFileBlockStore leafFileBlockStore;

	/**
	 * Current blockid/nodeid from underlying storage
	 */
	private int storageBlock = 0; // META-DATA: id of last blockid for nodes

	/**
	 * Max allocated Internal nodes
	 */
	private int maxInternalNodes = 0;

	/**
	 * Max allocated Leaf Nodes
	 */
	private int maxLeafNodes = 0;

	private boolean isDirect = true;

	private SeriAndDeser<K> keySeriAndDeser;
	private SeriAndDeser<V> valueSeriAndDeser;

	@SuppressWarnings("rawtypes")
	public BplusTree(int order, String dataFile, final SeriAndDeser<K> keySeriAndDeser,
			final SeriAndDeser<V> valueSeriAndDeser) {
		if (order < 3) {
			System.out.print("order must be greater than 2");
			System.exit(0);
		}
		this.order = order;
		this.keySeriAndDeser = keySeriAndDeser;
		this.valueSeriAndDeser = valueSeriAndDeser;
		fileStorage = new File(dataFile + ".data");
		freeBlocks = new SimpleBitSet();
		storage = new FileBlockStore(fileStorage.getAbsolutePath(), blockSize, isDirect);
		leafFileBlockStore = new LeafFileBlockStore(new File(dataFile + "_leaf.data"));
		if (!storage.open()) {
			System.out.print("fileStorage" + fileStorage.getAbsolutePath() + " can not open");
			return;
		}
		if (!leafFileBlockStore.open()) {
			System.out.print("leafFileBlockStore" + fileStorage.getAbsolutePath() + " can not open");
			return;
		}
		this.onRemoveListener = new OldValueRemoveListener<BplusNode>() {
			@SuppressWarnings("unchecked")
			@Override
			public void onRemove(BplusNode node) {
				boolean isLeaf = node.isLeaf;
				final int nodeid = node.id;
				final int index = (nodeid < 0 ? -nodeid : nodeid);
				// System.out.println("======== cache node:" + node.id +
				// ",isLeaf:" + isLeaf);
				if (isLeaf) {
					LeafWriteBuffer wb = leafFileBlockStore.set(index, node.sizeOfData());
					ByteBuffer buf = wb.buf();
					if (node.isDelete) {
						int cx = (buf.capacity() >> 3); // division by 8
						while (cx-- > 0) {
							buf.put((byte) 0); // Fill with zeroes
						}
						buf.flip();
						leafFileBlockStore.freeCache(index);
						freeBlocks.set(index); // Mark block as free
					} else {
						BplusNode.serialize(keySeriAndDeser, valueSeriAndDeser, buf, node);
						buf.flip();
					}
					wb.save();
					leafFileBlockStore.sync();
				} else {
					final WriteBuffer wbuf = storage.set(index);
					final ByteBuffer buf = wbuf.buf();
					if (node.isDelete) { // This block is for delete
						buf.clear();
						int cx = (blockSize >> 3); // division by 8
						while (cx-- > 0) {
							buf.putLong(0); // Fill with zeroes
						}
						buf.flip();
						freeBlocks.set(index); // Mark block as free
					} else {
						BplusNode.serialize(keySeriAndDeser, valueSeriAndDeser, buf, node);
						buf.flip();
					}
					wbuf.save();
					storage.sync();
				}
			}
		};
		cacheInternalNodes = createCacheLRUlinked(128); // 128
		cacheLeafNodes = createCacheLRUlinked(127); // 127
		rootId = new BplusNode<K, V>(true, true, this).id;
		headId = rootId;
	}

	protected int allocNode(final boolean isLeaf) {
		// 从0位置开始获取一个非0位置
		int id = freeBlocks.nextSetBit(0);
		if (id < 0) {
			if (isLeaf) {
				maxLeafNodes++;
			} else {
				maxInternalNodes++;
			}
			id = ++storageBlock;
		} else {
			// 如果存在,将该位置置为0,说明非空,不是空闲的
			freeBlocks.clear(id);
		}
		return (isLeaf ? id : -id);
	}

	/**
	 * Create a LRU hashmap of size maxSize
	 * 
	 * @param maxSize
	 * @return IntLinkedHashMap
	 */
	@SuppressWarnings("rawtypes")
	private IntLinkedHashMap<BplusNode> createCacheLRUlinked(final int maxSize) {
		IntLinkedHashMap<BplusNode> intLinkedHashMap = new IntLinkedHashMap<BplusNode>((int) (maxSize * 1.5f),
				BplusNode.class, true);
		intLinkedHashMap.setOldValueRemoveListener(this.onRemoveListener);
		return intLinkedHashMap;
	}

	protected BplusNode<K, V> getNode(final int nodeid) {
		if (nodeid == BplusNode.NULL_ID) {
			// log.debug(this.getClass().getName() + "::getNode(" + nodeid +
			// ")ERROR");
			return null;
		}
		return findNodeFromCache(nodeid);
	}

	/**
	 * Get node from file
	 * 
	 * @param nodeid
	 *            int with nodeid
	 * @return Node<K,V>
	 */
	private BplusNode<K, V> getNodeFromStore(final int nodeid) {
		boolean isLeaf = nodeid > 0;
		final int index = nodeid < 0 ? -nodeid : nodeid;
		BplusNode<K, V> node = null;
		if (isLeaf) {
			final ByteBuffer buf = this.leafFileBlockStore.get(index);
			node = BplusNode.deserialize(this.keySeriAndDeser, this.valueSeriAndDeser, buf, isLeaf, this);
			// System.out.println("leaf deserialize:" + node);
		} else {
			final ByteBuffer buf = storage.get(index);
			node = BplusNode.deserialize(this.keySeriAndDeser, this.valueSeriAndDeser, buf, isLeaf, this);
			// System.out.println("inner deserialize:" + node);
			storage.release(buf);
		}
		return node;
	}

	protected void cacheNode(BplusNode<K, V> node) {
		// System.out.println("cacheNode:" + node.id + " , isLeaf:" +
		// (node.isLeaf));
		if (node.isLeaf) {
			this.cacheLeafNodes.put(node.id, node);
		} else {
			this.cacheInternalNodes.put(node.id, node);
		}
	}

	@SuppressWarnings("unchecked")
	protected BplusNode<K, V> findNodeFromCache(int nodeId) {
		// System.out.println("findNodeFromCache:" + nodeId + " , isLeaf:" +
		// (nodeId > 0));
		BplusNode<K, V> node = (nodeId > 0 ? cacheLeafNodes : cacheInternalNodes).get(nodeId);
		if (node == null) {
			node = getNodeFromStore(nodeId);
			(node.isLeaf ? cacheLeafNodes : cacheInternalNodes).put(nodeId, node);
		}
		return node;
	}

	/**
	 * @param b
	 * @return
	 */
	public BplusNode<K, V> createNode(boolean isLeaf) {
		return new BplusNode<>(isLeaf, false, this);
	}


	/**
	 * @param startOffset
	 * @param batchSize
	 * @return
	 */
	public List<V> listData(Integer startOffset, Integer batchSize) {
		int index = 0;
		List<V> result = new LinkedList<>();
		BplusNode<K, V> tempNode = this.getHead();
		do {
			if (index >= startOffset) {
				index = startOffset - index;
				break;
			}
			index += tempNode.entries.size();
		} while ((tempNode = this.getNode(tempNode.nextNodeId)) != null);
		boolean isBreak = false;
		do {
			int readSize = tempNode.entries.size();
			if (result.size() + tempNode.entries.size() > startOffset + batchSize) {
				readSize = (startOffset + batchSize - result.size());
				isBreak = true;
			}
			for (int i = 0; i < readSize; i++) {
				result.add(tempNode.entries.get(i).getValue());
			}
		} while (!isBreak && (tempNode = this.getNode(tempNode.nextNodeId)) != null);
		return result;
	}

	@Override
	public Iterator<V> iterator() {
		return new BPlusIterator();
	}

	private class BPlusIterator implements Iterator<V> {
		BplusNode<K, V> tempNode = getHead();
		int currentNodeIndex = 0;
		int currentNodeSize = tempNode.entries.size();

		Entry<K, V> loastEntry;

		@Override
		public boolean hasNext() {
			return tempNode != null && currentNodeIndex < currentNodeSize;
		}

		@Override
		public V next() {
			loastEntry = this.tempNode.entries.get(currentNodeIndex++);
			if (this.currentNodeIndex >= this.currentNodeSize && this.tempNode.nextNodeId != BplusNode.NULL_ID) {
				this.tempNode = getNode(this.tempNode.nextNodeId);
				this.currentNodeIndex = 0;
				if (this.tempNode == null) {
					System.out.println("tempNode:" + tempNode);
				} else {
					if (this.tempNode.entries == null) {
						System.out.println("tempNode.entries:" + tempNode.id);
					}
					this.currentNodeSize = tempNode.entries.size();
				}
			}
			return loastEntry.getValue();
		}

		// 删除，使用this 调用类的方法而不是内部类的方法
		@Override
		public void remove() {
			System.out.println("======remove:" + loastEntry.getKey());
			BplusTree.this.remove(loastEntry.getKey());
		}
	}

	public void checkLinkedIsRightBeforeRemove(BplusNode<K, V> node) {
		if (node == null) {
			return;
		}
		if (node.isLeaf) {
			int previousNodeId = node.previousNodeId;
			int nextNodeId = node.nextNodeId;
			if (previousNodeId == BplusNode.NULL_ID && node.id != headId) {
				System.out.print("nodeId:" + node.id + " ---- is not right");
				return;
			}
			BplusNode<K, V> preNode = getNode(previousNodeId);
			BplusNode<K, V> nextNode = getNode(nextNodeId);
			boolean isRight = false;
			if ((preNode != null && preNode.parentNodeId == node.parentNodeId)
					|| (nextNode != null && nextNode.parentNodeId == node.parentNodeId)) {
				isRight = true;
			}
			if (!isRight) {
				System.out.print("nodeId:" + node.id + " is not right");
				return;
			}
			// System.out.println("node with id:" + node.id + " has checkout");
		} else {
			for (int id : node.children) {
				this.checkLinkedIsRightBeforeRemove(this.getNode(id));
			}
		}

	}

}
