package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache;

import java.nio.ByteBuffer;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月17日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class BplusNode<K extends Comparable<K> & DataSizeCountable, V extends DataSizeCountable>
		implements DataSizeCountable {

	public static final int NULL_ID = -1;

	private BplusTree<K, V> tree;

	/** 是否为叶子节点 */
	protected boolean isLeaf;
	/** 是否为根节点 */
	protected boolean isRoot;
	protected boolean isDelete;
	/** 父节点Id */
	protected int parentNodeId = NULL_ID;
	/** 叶节点的前节点Id */
	protected int previousNodeId = NULL_ID;
	/** 叶节点的后节点Id */
	protected int nextNodeId = NULL_ID;
	/** 节点的关键字 */
	protected List<Entry<K, V>> entries;
	/** 子节点 */
	protected List<Integer> children;

	public int id;

	private BplusNode() {
	}

	public BplusNode(boolean isLeaf, boolean isRoot, BplusTree<K, V> tree) {
		this.isLeaf = isLeaf;
		this.tree = tree;
		entries = new ArrayList<Entry<K, V>>();
		if (!isLeaf) {
			children = new ArrayList<>();
		}
		this.isRoot = isRoot;
		this.id = this.allocId();
		tree.cacheNode(this);
	}

	public int allocId() {
		return tree.allocNode(this.isLeaf);
	}

	public V get(K key) {
		// 如果是叶子节点
		if (isLeaf) {
			int low = 0, high = entries.size() - 1, mid;
			int comp;
			while (low <= high) {
				mid = (low + high) / 2;
				comp = entries.get(mid).getKey().compareTo(key);
				if (comp == 0) {
					return entries.get(mid).getValue();
				} else if (comp < 0) {
					low = mid + 1;
				} else {
					high = mid - 1;
				}
			}
			// 未找到所要查询的对象
			return null;
		}
		// 如果不是叶子节点
		// 如果key小于节点最左边的key，沿第一个子节点继续搜索
		if (key.compareTo(entries.get(0).getKey()) < 0) {
			return tree.getNode(children.get(0)).get(key);
			// 如果key大于等于节点最右边的key，沿最后一个子节点继续搜索
		} else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
			return tree.getNode(children.get(children.size() - 1)).get(key);
			// 否则沿比key大的前一个子节点继续搜索
		} else {
			int low = 0, high = entries.size() - 1, mid = 0;
			int comp;
			while (low <= high) {
				mid = (low + high) / 2;
				comp = entries.get(mid).getKey().compareTo(key);
				if (comp == 0) {
					return tree.getNode(children.get(mid + 1)).get(key);
				} else if (comp < 0) {
					low = mid + 1;
				} else {
					high = mid - 1;
				}
			}
			return tree.getNode(children.get(low)).get(key);
		}
	}

	public void insertOrUpdate(K key, V value, BplusTree<K, V> tree) {
		// 如果是叶子节点
		if (isLeaf) {
			// 不需要分裂，直接插入或更新
			if (contains(key) != -1 || entries.size() < tree.getOrder()) {
				insertOrUpdate(key, value);
				if (tree.getHeight() == 0) {
					tree.setHeight(1);
				}
				return;
			}
			// 需要分裂
			// 分裂成左右两个节点
			BplusNode<K, V> left = tree.createNode(true);
			BplusNode<K, V> right = tree.createNode(true);
			// 设置链接
			if (this.previousNodeId != BplusNode.NULL_ID) {
				getPrevious().nextNodeId = left.id;
				left.previousNodeId = this.previousNodeId;
			}
			if (this.nextNodeId != BplusNode.NULL_ID) {
				this.getNextNode().previousNodeId = right.id;
				right.nextNodeId = nextNodeId;
			}
			if (this.previousNodeId == BplusNode.NULL_ID) {
				tree.setHead(left);
			}

			left.nextNodeId = right.id;
			right.previousNodeId = left.id;
			previousNodeId = BplusNode.NULL_ID;
			nextNodeId = BplusNode.NULL_ID;

			// 复制原节点关键字到分裂出来的新节点
			copy2Nodes(key, value, left, right, tree);
			// tree.removeCache(this);
			// 如果不是根节点
			if (parentNodeId != BplusNode.NULL_ID) {
				// 调整父子节点关系
				int index = getParent().children.indexOf(this.id);
				getParent().children.remove(index);
				left.parentNodeId = parentNodeId;
				right.parentNodeId = parentNodeId;
				getParent().children.add(index, left.id);
				getParent().children.add(index + 1, right.id);
				getParent().entries.add(index, right.entries.get(0));
				entries = null; // 删除当前节点的关键字信息
				children = null; // 删除当前节点的孩子节点引用

				// 父节点插入或更新关键字
				getParent().updateInsert(tree);
				parentNodeId = BplusNode.NULL_ID; // 删除当前节点的父节点引用
				// 如果是根节点
			} else {
				isRoot = false;
				BplusNode<K, V> parent = new BplusNode<K, V>(false, true, tree);
				tree.setRoot(parent);
				left.parentNodeId = parent.id;
				right.parentNodeId = parent.id;
				parent.children.add(left.id);
				parent.children.add(right.id);
				parent.entries.add(right.entries.get(0));
				entries = null;
				children = null;
			}
			return;
		}
		// 如果不是叶子节点
		// 如果key小于等于节点最左边的key，沿第一个子节点继续搜索
		if (key.compareTo(entries.get(0).getKey()) < 0) {
			tree.getNode(children.get(0)).insertOrUpdate(key, value, tree);
			// 如果key大于节点最右边的key，沿最后一个子节点继续搜索
		} else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
			tree.getNode(children.get(children.size() - 1)).insertOrUpdate(key, value, tree);
			// 否则沿比key大的前一个子节点继续搜索
		} else {
			int low = 0, high = entries.size() - 1, mid = 0;
			int comp;
			while (low <= high) {
				mid = (low + high) / 2;
				comp = entries.get(mid).getKey().compareTo(key);
				if (comp == 0) {
					tree.getNode(children.get(mid + 1)).insertOrUpdate(key, value, tree);
					break;
				} else if (comp < 0) {
					low = mid + 1;
				} else {
					high = mid - 1;
				}
			}
			if (low > high) {
				tree.getNode(children.get(low)).insertOrUpdate(key, value, tree);
			}
		}
	}

	/**
	 * @return
	 */
	private BplusNode<K, V> getParent() {
		return this.parentNodeId != BplusNode.NULL_ID ? tree.getNode(this.parentNodeId) : null;
	}

	/**
	 * 
	 */
	private BplusNode<K, V> getNextNode() {
		return this.nextNodeId != BplusNode.NULL_ID ? tree.getNode(this.nextNodeId) : null;
	}

	/**
	 * @return
	 */
	private BplusNode<K, V> getPrevious() {
		return this.previousNodeId != BplusNode.NULL_ID ? tree.getNode(this.previousNodeId) : null;
	}

	private void copy2Nodes(K key, V value, BplusNode<K, V> left, BplusNode<K, V> right, BplusTree<K, V> tree) {
		// 左右两个节点关键字长度
		int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;
		boolean b = false;// 用于记录新元素是否已经被插入
		for (int i = 0; i < entries.size(); i++) {
			if (leftSize != 0) {
				leftSize--;
				if (!b && entries.get(i).getKey().compareTo(key) > 0) {
					left.entries.add(new SimpleEntry<K, V>(key, value));
					b = true;
					i--;
				} else {
					left.entries.add(entries.get(i));
				}
			} else {
				if (!b && entries.get(i).getKey().compareTo(key) > 0) {
					right.entries.add(new SimpleEntry<K, V>(key, value));
					b = true;
					i--;
				} else {
					right.entries.add(entries.get(i));
				}
			}
		}
		if (!b) {
			right.entries.add(new SimpleEntry<K, V>(key, value));
		}
	}

	/** 插入节点后中间节点的更新 */
	protected void updateInsert(BplusTree<K, V> tree) {
		// 如果子节点数超出阶数，则需要分裂该节点
		if (children.size() > tree.getOrder()) {
			// 分裂成左右两个节点
			BplusNode<K, V> left = tree.createNode(false);
			BplusNode<K, V> right = tree.createNode(false);
			// 左右两个节点子节点的长度
			int leftSize = (tree.getOrder() + 1) / 2 + (tree.getOrder() + 1) % 2;
			int rightSize = (tree.getOrder() + 1) / 2;
			// 复制子节点到分裂出来的新节点，并更新关键字
			for (int i = 0; i < leftSize; i++) {
				left.children.add(children.get(i));
				tree.getNode(children.get(i)).parentNodeId = left.id;
			}
			for (int i = 0; i < rightSize; i++) {
				right.children.add(children.get(leftSize + i));
				tree.getNode(children.get(leftSize + i)).parentNodeId = right.id;
			}
			for (int i = 0; i < leftSize - 1; i++) {
				left.entries.add(entries.get(i));
			}
			for (int i = 0; i < rightSize - 1; i++) {
				right.entries.add(entries.get(leftSize + i));
			}
			// tree.removeCache(this);
			// 如果不是根节点
			if (parentNodeId != BplusNode.NULL_ID) {
				// 调整父子节点关系
				int index = getParent().children.indexOf(this.id);
				getParent().children.remove(index);
				left.parentNodeId = parentNodeId;
				right.parentNodeId = parentNodeId;
				getParent().children.add(index, left.id);
				getParent().children.add(index + 1, right.id);
				getParent().entries.add(index, entries.get(leftSize - 1));
				entries = null;
				children = null;

				// 父节点更新关键字
				getParent().updateInsert(tree);
				parentNodeId = BplusNode.NULL_ID;
				// 如果是根节点
			} else {
				isRoot = false;
				BplusNode<K, V> parent = new BplusNode<K, V>(false, true, tree);
				tree.setRoot(parent);
				tree.setHeight(tree.getHeight() + 1);
				left.parentNodeId = parent.id;
				right.parentNodeId = parent.id;
				parent.children.add(left.id);
				parent.children.add(right.id);
				parent.entries.add(entries.get(leftSize - 1));
				entries = null;
				children = null;
			}
		}
	}

	/** 删除节点后中间节点的更新 */
	protected void updateRemove(BplusTree<K, V> tree) {
		// 如果子节点数小于M / 2或者小于2，则需要合并节点
		if (children.size() < tree.getOrder() / 2 || children.size() < 2) {
			if (isRoot) {
				// 如果是根节点并且子节点数大于等于2，OK
				if (children.size() >= 2)
					return;
				// 否则与子节点合并
				BplusNode<K, V> root = tree.getNode(children.get(0));
				tree.setRoot(root);
				tree.setHeight(tree.getHeight() - 1);
				root.parentNodeId = BplusNode.NULL_ID;
				root.isRoot = true;
				entries = null;
				children = null;
				return;
			}
			// 计算前后节点
			int currIdx = getParent().children.indexOf(this.id);
			int prevIdx = currIdx - 1;
			int nextIdx = currIdx + 1;
			BplusNode<K, V> previous = null, next = null;
			if (prevIdx >= 0) {
				previous = tree.getNode(getParent().children.get(prevIdx));
			}
			if (nextIdx < getParent().children.size()) {
				next = tree.getNode(getParent().children.get(nextIdx));
			}

			// 如果前节点子节点数大于M / 2并且大于2，则从其处借补
			if (previous != null && previous.children.size() > tree.getOrder() / 2 && previous.children.size() > 2) {
				// 前叶子节点末尾节点添加到首位
				int idx = previous.children.size() - 1;
				BplusNode<K, V> borrow = tree.getNode(previous.children.get(idx));
				previous.children.remove(idx);
				borrow.parentNodeId = this.id;
				children.add(0, borrow.id);
				int preIndex = getParent().children.indexOf(previous.id);

				entries.add(0, getParent().entries.get(preIndex));
				getParent().entries.set(preIndex, previous.entries.remove(idx - 1));
				return;
			}

			// 如果后节点子节点数大于M / 2并且大于2，则从其处借补
			if (next != null && next.children.size() > tree.getOrder() / 2 && next.children.size() > 2) {
				// 后叶子节点首位添加到末尾
				BplusNode<K, V> borrow = tree.getNode(next.children.get(0));
				next.children.remove(0);
				borrow.parentNodeId = this.id;
				children.add(borrow.id);
				int preIndex = getParent().children.indexOf(this.id);
				entries.add(getParent().entries.get(preIndex));
				getParent().entries.set(preIndex, next.entries.remove(0));
				return;
			}

			// 同前面节点合并
			if (previous != null
					&& (previous.children.size() <= tree.getOrder() / 2 || previous.children.size() <= 2)) {
				for (int i = 0; i < children.size(); i++) {
					previous.children.add(children.get(i));
				}
				for (int i = 0; i < previous.children.size(); i++) {
					tree.getNode(previous.children.get(i)).parentNodeId = this.id;
				}
				int indexPre = getParent().children.indexOf(previous.id);
				previous.entries.add(getParent().entries.get(indexPre));
				for (int i = 0; i < entries.size(); i++) {
					previous.entries.add(entries.get(i));
				}
				children = previous.children;
				entries = previous.entries;

				// 更新父节点的关键字列表
				getParent().children.remove(getParent().children.indexOf(previous.id));
				previous.parentNodeId = BplusNode.NULL_ID;
				previous.children = null;
				previous.entries = null;
				getParent().entries.remove(getParent().children.indexOf(this.id));
				if ((!getParent().isRoot
						&& (getParent().children.size() >= tree.getOrder() / 2 && getParent().children.size() >= 2))
						|| getParent().isRoot && getParent().children.size() >= 2) {
					return;
				}
				getParent().updateRemove(tree);
				return;
			}

			// 同后面节点合并
			if (next != null && (next.children.size() <= tree.getOrder() / 2 || next.children.size() <= 2)) {
				for (int i = 0; i < next.children.size(); i++) {
					BplusNode<K, V> child = tree.getNode(next.children.get(i));
					children.add(child.id);
					child.parentNodeId = this.id;
				}
				int index = getParent().children.indexOf(this.id);
				entries.add(getParent().entries.get(index));
				for (int i = 0; i < next.entries.size(); i++) {
					entries.add(next.entries.get(i));
				}
				getParent().children.remove(getParent().children.indexOf(next.id));
				next.parentNodeId = BplusNode.NULL_ID;
				next.children = null;
				next.entries = null;
				getParent().entries.remove(getParent().children.indexOf(this.id));
				if ((!getParent().isRoot
						&& (getParent().children.size() >= tree.getOrder() / 2 && getParent().children.size() >= 2))
						|| getParent().isRoot && getParent().children.size() >= 2) {
					return;
				}
				getParent().updateRemove(tree);
				return;
			}
		}
	}

	public V remove(K key, BplusTree<K, V> tree) {
		// 如果是叶子节点
		if (isLeaf) {
			// 如果不包含该关键字，则直接返回
			if (contains(key) == -1) {
				return null;
			}
			// 如果既是叶子节点又是根节点，直接删除
			if (isRoot) {
				if (entries.size() == 1) {
					tree.setHeight(0);
				}
				return remove(key);
			}
			// 如果关键字数大于M / 2，直接删除
			if (entries.size() > tree.getOrder() / 2 && entries.size() > 2) {
				return remove(key);
			}
			// 如果自身关键字数小于M / 2，并且前节点关键字数大于M / 2，则从其处借补
			if (this.getPrevious() != null && getPrevious().parentNodeId == parentNodeId
					&& getPrevious().entries.size() > tree.getOrder() / 2 && getPrevious().entries.size() > 2) {
				// 添加到首位
				int size = getPrevious().entries.size();
				entries.add(0, getPrevious().entries.remove(size - 1));
				int index = this.getParent().children.indexOf(getPrevious().id);
				this.getParent().entries.set(index, entries.get(0));
				return remove(key);
			}
			// 如果自身关键字数小于M / 2，并且后节点关键字数大于M / 2，则从其处借补
			if (this.getNextNode() != null && getNextNode().parentNodeId == parentNodeId
					&& getNextNode().entries.size() > tree.getOrder() / 2 && getNextNode().entries.size() > 2) {
				entries.add(getNextNode().entries.remove(0));
				int index = this.getParent().children.indexOf(this.id);
				this.getParent().entries.set(index, getNextNode().entries.get(0));
				return remove(key);
			}
			// 同前面节点合并
			if (getPrevious() != null && getPrevious().parentNodeId == parentNodeId
					&& (getPrevious().entries.size() <= tree.getOrder() / 2 || getPrevious().entries.size() <= 2)) {
				V returnValue = remove(key);
				for (int i = 0; i < entries.size(); i++) {
					// 将当前节点的关键字添加到前节点的末尾
					getPrevious().entries.add(entries.get(i));
				}
				entries = getPrevious().entries;
				this.getParent().children.remove(this.getParent().children.indexOf(getPrevious().id));
				getPrevious().parentNodeId = BplusNode.NULL_ID;
				getPrevious().entries = null;
				// 更新链表
				if (getPrevious().previousNodeId != BplusNode.NULL_ID) {
					BplusNode<K, V> temp = getPrevious();
					getNode(temp.previousNodeId).nextNodeId = this.id;
					previousNodeId = temp.previousNodeId;
					temp.previousNodeId = BplusNode.NULL_ID;
					temp.nextNodeId = BplusNode.NULL_ID;
				} else {
					tree.setHead(this);
					getPrevious().nextNodeId = BplusNode.NULL_ID;
					previousNodeId = BplusNode.NULL_ID;
				}
				this.getParent().entries.remove(this.getParent().children.indexOf(this.id));
				if ((!this.getParent().isRoot && (this.getParent().children.size() >= tree.getOrder() / 2
						&& this.getParent().children.size() >= 2))
						|| this.getParent().isRoot && this.getParent().children.size() >= 2) {
					return returnValue;
				}
				this.getParent().updateRemove(tree);
				return returnValue;
			}
			// 同后面节点合并
			if (this.getNextNode() != null && this.getNextNode().parentNodeId == parentNodeId
					&& (this.getNextNode().entries.size() <= tree.getOrder() / 2
							|| this.getNextNode().entries.size() <= 2)) {
				V returnValue = remove(key);
				for (int i = 0; i < this.getNextNode().entries.size(); i++) {
					// 从首位开始添加到末尾
					entries.add(this.getNextNode().entries.get(i));
				}
				this.getNextNode().parentNodeId = BplusNode.NULL_ID;
				this.getNextNode().entries = null;
				this.getParent().children.remove(this.getParent().children.indexOf(this.nextNodeId));
				// 更新链表
				if (this.getNextNode().nextNodeId != BplusNode.NULL_ID) {
					BplusNode<K, V> temp = this.getNextNode();
					this.getNode(temp.nextNodeId).previousNodeId = this.id;
					this.nextNodeId = temp.nextNodeId;
					temp.previousNodeId = BplusNode.NULL_ID;
					temp.nextNodeId = BplusNode.NULL_ID;
				} else {
					this.getNextNode().previousNodeId = BplusNode.NULL_ID;
					nextNodeId = BplusNode.NULL_ID;
				}
				// 更新父节点的关键字列表
				this.getParent().entries.remove(this.getParent().children.indexOf(this.id));
				if ((!this.getParent().isRoot && (this.getParent().children.size() >= tree.getOrder() / 2
						&& this.getParent().children.size() >= 2))
						|| this.getParent().isRoot && this.getParent().children.size() >= 2) {
					return returnValue;
				}
				this.getParent().updateRemove(tree);
				return returnValue;
			}
		}
		/* 如果不是叶子节点 */

		// 如果key小于等于节点最左边的key，沿第一个子节点继续搜索
		if (key.compareTo(entries.get(0).getKey()) < 0) {
			return tree.getNode(children.get(0)).remove(key, tree);
			// 如果key大于节点最右边的key，沿最后一个子节点继续搜索
		} else if (key.compareTo(entries.get(entries.size() - 1).getKey()) >= 0) {
			return tree.getNode(children.get(children.size() - 1)).remove(key, tree);
			// 否则沿比key大的前一个子节点继续搜索
		} else {
			int low = 0, high = entries.size() - 1, mid = 0;
			int comp;
			while (low <= high) {
				mid = (low + high) / 2;
				comp = entries.get(mid).getKey().compareTo(key);
				if (comp == 0) {
					if (children.size() < entries.size() - 1) {
						// TODO
						// this.remove(key, tree); // just for test
						System.out.println("STOP!!!");
					}
					return tree.getNode(children.get(mid + 1)).remove(key, tree);
				} else if (comp < 0) {
					low = mid + 1;
				} else {
					high = mid - 1;
				}
			}
			return tree.getNode(children.get(low)).remove(key, tree);
		}
	}

	/**
	 * @param nodeId
	 * @return
	 */
	private BplusNode<K, V> getNode(int nodeId) {
		return this.tree.getNode(nodeId);
	}

	/** 判断当前节点是否包含该关键字 */
	protected int contains(K key) {
		int low = 0, high = entries.size() - 1, mid;
		int comp;
		while (low <= high) {
			mid = (low + high) / 2;
			comp = entries.get(mid).getKey().compareTo(key);
			if (comp == 0) {
				return mid;
			} else if (comp < 0) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return -1;
	}

	/** 插入到当前节点的关键字中 */
	protected void insertOrUpdate(K key, V value) {
		// 二叉查找，插入
		int low = 0, high = entries.size() - 1, mid;
		int comp;
		while (low <= high) {
			mid = (low + high) / 2;
			comp = entries.get(mid).getKey().compareTo(key);
			if (comp == 0) {
				entries.get(mid).setValue(value);
				break;
			} else if (comp < 0) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		if (low > high) {
			entries.add(low, new SimpleEntry<K, V>(key, value));
		}
	}

	/** 删除节点 */
	protected V remove(K key) {
		int low = 0, high = entries.size() - 1, mid;
		int comp;
		while (low <= high) {
			mid = (low + high) / 2;
			comp = entries.get(mid).getKey().compareTo(key);
			if (comp == 0) {
				Entry<K, V> remove = entries.remove(mid);
				return remove.getValue();
			} else if (comp < 0) {
				low = mid + 1;
			} else {
				high = mid - 1;
			}
		}
		return null;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("is: ");
		sb.append(id);
		sb.append(", ");
		sb.append("isRoot: ");
		sb.append(isRoot);
		sb.append(", ");
		sb.append("isLeaf: ");
		sb.append(isLeaf);
		sb.append(", ");
		sb.append("keys: ");
		if (entries != null) {
			for (Entry<K, V> entry : entries) {
				sb.append(entry.getKey());
				sb.append(", ");
			}
		}
		sb.append(", ");
		return sb.toString();
	}

	public static <K extends Comparable<K> & DataSizeCountable, V extends DataSizeCountable> void serialize(
			SeriAndDeser<K> keySeriaAndDeser, SeriAndDeser<V> valueSeriaAndDeser, ByteBuffer buf,
			BplusNode<K, V> node) {
		buf.put(node.isLeaf ? (byte) 0 : (byte) 1);
		buf.put(node.isRoot ? (byte) 0 : (byte) 1);
		buf.putInt(node.id);
		buf.putInt(node.parentNodeId);
		buf.putInt(node.previousNodeId);
		buf.putInt(node.nextNodeId);
		List<Entry<K, V>> nodeEntities = node.entries;
		if (nodeEntities != null && nodeEntities.size() != 0) {
			buf.putInt(nodeEntities.size());
			for (Entry<K, V> entry : nodeEntities) {
				K key = entry.getKey();
				keySeriaAndDeser.serialize(key, buf);
				if (node.isLeaf) {
					valueSeriaAndDeser.serialize(entry.getValue(), buf);
				}
			}
		} else {
			buf.putInt(0);
		}
		List<Integer> children = node.children;
		if (children != null && children.size() != 0) {
			buf.putInt(children.size());
			for (Integer childNodeId : children) {
				buf.putInt(childNodeId);
			}
		} else {
			buf.putInt(0);
		}
	}

	public static <K extends Comparable<K> & DataSizeCountable, V extends DataSizeCountable> BplusNode<K, V> deserialize(
			SeriAndDeser<K> keySeriaAndDeser, SeriAndDeser<V> valueSeriaAndDeser, ByteBuffer buf, boolean isLeaf,
			BplusTree<K, V> tree) {
		BplusNode<K, V> node = new BplusNode<>();
		node.isLeaf = buf.get() == 0 ? true : false;
		node.isRoot = buf.get() == 0 ? true : false;
		node.id = buf.getInt();
		node.parentNodeId = buf.getInt();
		node.previousNodeId = buf.getInt();
		node.nextNodeId = buf.getInt();
		int entityNums = buf.getInt();
		if (entityNums != 0) {
			List<Entry<K, V>> nodeEntities = new ArrayList<>(entityNums);
			for (int i = 0; i < entityNums; i++) {
				K key = keySeriaAndDeser.deserialize(buf);
				V value = null;
				if (node.isLeaf) {
					value = valueSeriaAndDeser.deserialize(buf);
				}
				nodeEntities.add(new SimpleEntry<K, V>(key, value));
			}
			node.entries = nodeEntities;
		} else {
			node.entries = new ArrayList<>(0);
		}
		int childNums = buf.getInt();
		if (childNums > 0) {
			List<Integer> children = new ArrayList<>(childNums);
			for (int i = 0; i < childNums; i++) {
				children.add(buf.getInt());
			}
			node.children = children;
		} else {
			node.children = new ArrayList<>(0);
		}
		if (!node.isLeaf && node.children.size() < node.entries.size() - 1) {
			System.out.println("STOP!!!");
		}
		node.tree = tree;
		return node;
	}

	@Override
	public int sizeOfData() {
		int size = 0;
		size += 1;
		size += 1;
		size += 4;
		size += 4;
		size += 4;
		size += 4;
		List<Entry<K, V>> nodeEntities = this.entries;
		size += 4;
		if (nodeEntities != null && nodeEntities.size() != 0) {
			for (Entry<K, V> entry : nodeEntities) {
				K key = entry.getKey();
				size += key.sizeOfData();
				if (this.isLeaf) {
					if (entry.getValue() instanceof DataSizeCountable) {
						size += entry.getValue().sizeOfData();
					}
				}
			}
		}
		size += 4;
		return size;
	}

}
