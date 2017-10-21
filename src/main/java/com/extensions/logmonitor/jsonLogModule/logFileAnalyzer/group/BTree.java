package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Comparator;

/**
 *
 * @author Anthony
 */
public class BTree<T extends Comparable<T>> {

	static protected int order;
	static private int maxValues;
	static private final int blockSize = 4096;
	static protected File f;// = new File("inputs.dat");
	static boolean loadedFromFile;
	Node<T> root;
	static public RandomAccessFile raf;
	static protected int numOfBlock = 0;
	static protected int lastBlockNum;
	protected int totalKeys = 0;
	protected int totalValues = 0;
	public Comparator<T> bcmp = new Comparator<T>() {
		@Override
		public int compare(T a, T b) {
			return ((Comparable<T>) a).compareTo(b);
		}
	};

	public BTree(int order) throws IOException {
		BTree.order = order;
		raf = new RandomAccessFile(f, "rwd");
		// 包括关键字键和指向子节点的指针键
		maxValues = (2 * order) - 1;
		root = null;
	}

	public BTree(int order, File f) throws IOException {
		BTree.order = order;
		BTree.f = f;
		raf = new RandomAccessFile(f, "rwd");
		maxValues = (2 * order) - 1;
		numOfBlock = 0;
		root = null;
	}

	public void close() throws IOException {
		raf.close();
	}

	public void load() throws IOException {
		if (raf.length() != 0) {
			raf.seek(0);
			if (root == null)
				root = new Node<>(order, true, numOfBlock++);
			root.diskRead();
			lastBlockNum = root.getLastBlockNum();
			root.load();
		}
	}

	public void reWrite() throws IOException {
		if (root != null)
			root.reWrite();
	}

	public void traverse() throws IOException {
		if (root != null)
			root.traverse();
	}

	public boolean find(T k) throws IOException {
		return root.containsVal(k);
	}

	public boolean findKey(T u) throws IOException {
		return root.containsKey(u);
	}

	public void display(T k) throws IOException {
		if (root != null)
			root.display(k);
	}

	public void displayAllDataFromKey(T k) throws IOException {
		if (root != null)
			root.displayAllDataFromKey(k);
	}

	/**
	 * B树插入操作
	 * 
	 * @param k
	 * @param u
	 * @throws IOException
	 */
	public void insert(T k, T u) throws IOException {
		if (root == null) {
			root = new Node<>(order, true, numOfBlock++);
			root.values[0] = k;
			root.keys[0] = u;
			root.numOfValues = 1;
			root.diskWrite();
		} else {
			// 如果当前节点的节点数等于最大节点数
			if (root.numOfValues == maxValues) {
				// 进行分裂,构建新的IndexNode,文件块递增
				Node<T> newNode = new Node<>(order, false, numOfBlock++);
				// 新IndexNode的子节点0为当前节点
				newNode.children[0] = root;
				// 对当前节点(older)进行分裂
				newNode.splitChild(0, root);
				int i = 0;
				// 如果当前待插入的值大于新建节点,插入到较老的节点中
				// 这里应该是keys吧,待确认 TODO
				int compareRes = bcmp.compare(newNode.values[0], k);
				if (compareRes < 0) {
					i++;
				}
				newNode.children[i].insertNonFull(k, u);
				root = newNode;
			} else {
				root.insertNonFull(k, u);
			}
		}
	}

	public T remove(T k) throws IOException {
		T removed = null;

		if (root == null)
			return removed;

		removed = root.remove(k);

		if ((root.numOfValues == 0) && (!root.isLeaf))
			root = root.children[0];

		return removed;
	}

	public boolean groupRemove(T k) throws IOException {
		while (root.searchSeq(k) != null) {
			root.remove(k);
		}
		return true;
	}

	private static class Node<T> {
		private T values[];
		private T keys[];
		private boolean isLeaf;
		private boolean isLoaded;
		private Node<T> children[];
		private int numOfValues;
		private int numOfChildren;
		private int degree;
		private long position;
		private int blockNum;
		private int nextBlockNums[];
		final static private int sizeOfValue = 16;
		final static private int sizeOfKey = 40;
		public Comparator<T> cmp = new Comparator<T>() {
			@SuppressWarnings("unchecked")
			@Override
			public int compare(T a, T b) {
				return ((Comparable<T>) a).compareTo(b);
			}
		};

		@SuppressWarnings("unchecked")
		private Node(int order, boolean isLeaf, int blockNum) throws IOException {
			this.degree = order;
			this.isLeaf = isLeaf;
			isLoaded = false;
			// 所属的文件块
			this.blockNum = blockNum;
			// 所属的位置
			position = blockNum * blockSize;
			numOfValues = 0;
			// m阶B树最多有的值数量
			values = (T[]) new Comparable[maxValues];
			// m阶B树最多有的键数量
			keys = (T[]) new Comparable[maxValues];
			// m阶B树最多有的子节点数量
			children = new Node[maxValues + 1];
			// m阶B树最多有的值数量，对应每个子节点
			nextBlockNums = new int[maxValues + 1];
		}

		@SuppressWarnings("unchecked")
		private boolean diskRead() throws IOException {
			// 如果已经load了,直接返回
			if (isLoaded) {
				return true;
			}
			// 定位到文本下标
			BTree.raf.seek(position);
			// 标记是否是叶子节点
			byte leafFlag;
			leafFlag = raf.readByte();
			isLeaf = (leafFlag == 1);
			// 获取值得数量
			numOfValues = raf.readInt();
			// 获取文件块的数量
			blockNum = raf.readInt();
			// 获取子节点数量
			numOfChildren = raf.readInt();
			// 子节点处于的文件块编号
			for (int i = 0; i < numOfChildren; i++) {
				nextBlockNums[i] = raf.readInt();
			}
			// 读取关键字值
			values = (T[]) new Comparable[maxValues];
			int i = 0;
			byte valBuf[] = new byte[sizeOfValue];
			while (i < numOfValues && i < maxValues) {
				raf.readFully(valBuf, 0, sizeOfValue);
				values[i] = (T) new String(valBuf);
				i++;
			}
			i = 0;
			// 读取数据值
			byte keyBuf[] = new byte[sizeOfKey];
			while (i < numOfValues && i < maxValues) {
				raf.readFully(keyBuf, 0, sizeOfKey);
				keys[i] = (T) new String(keyBuf);
				i++;
			}
			if (numOfValues > 0 && !isLeaf) {
				for (int j = 0; j < numOfChildren; j++) {
					Long childAddress = raf.readLong();
					children[j].position = childAddress;
				}
			}
			isLoaded = true;
			return true;
		}

		private boolean diskWrite() throws IOException {
			raf.seek(position);
			byte leafFlag = (byte) (isLeaf ? 1 : 0);
			raf.writeByte(leafFlag);
			raf.writeInt(numOfValues);
			raf.writeInt(blockNum);
			int numOfChild = getNumOfChildren();
			raf.writeInt(numOfChild);

			for (int i = 0; i < numOfChild; i++) {
				raf.writeInt(nextBlockNums[i]);
			}

			int i = 0;
			while ((i <= numOfValues) && (i < maxValues) && (values[i] != null)) {
				String data = values[i].toString();
				raf.writeBytes(data);
				i++;
			}
			i = 0;
			while ((i <= numOfValues) && (i < maxValues) && (keys[i] != null)) {
				String data = keys[i].toString();
				raf.writeBytes(data);
				i++;
			}
			if ((numOfValues > 0) && !isLeaf) {
				int numOfChildren = 0;
				while ((numOfChildren < children.length) && (children[numOfChildren] != null)) {
					numOfChildren++;
				}
				long childAddresses[] = new long[numOfChildren];
				long thisChildAddress[] = childAddresses;
				i = 0;
				while ((i < children.length) && (children[i] != null)) {
					thisChildAddress[i] = children[i].position;
					i++;
				}
				long longsWritten = 0;
				for (i = 0; (i < childAddresses.length); i++) {
					raf.writeLong(childAddresses[i]);
					longsWritten++;
				}
				if (numOfValues + 1 != longsWritten) {
					return false;
				}
			}
			return true;
		}

		private void free() {
			if (isLoaded) {
				for (int i = 0; (i < numOfValues); i++)
					values[i] = null;
				for (int i = 0; (i < maxValues + 1); i++)
					children[i] = null;
				isLoaded = false;
			}
		}

		private int getNumOfChildren() {
			int i = 0;
			int amount = 0;
			/*
			 * for(int i = 0; i < children.length; i++) if(children[i] != null)
			 * amount++;
			 */
			while (i < children.length && children[i] != null) {
				i++;
				amount++;
			}
			return amount;
		}

		private void load() throws IOException {
			if (blockNum == BTree.lastBlockNum)
				return;
			if (nextBlockNums[0] == 0)
				return;

			for (int i = 0; nextBlockNums[i] != 0 && i < nextBlockNums.length; i++) {
				children[i] = new Node<>(order, true, nextBlockNums[i]);
				blockNum = nextBlockNums[i];
				children[i].diskRead();
				children[i].load();
			}

		}

		private void reWrite() throws IOException {
			if (blockNum == 0) {
				findNextBlockNums();
				diskWrite();
			}

			int i;
			for (i = 0; children[i] != null && i < children.length; i++)
				if (!isLeaf) {
					children[i].findNextBlockNums();
					children[i].diskWrite();
					children[i].reWrite();
				}

			if (!isLeaf && children[i] != null) {
				children[i].findNextBlockNums();
				children[i].diskWrite();
				children[i].reWrite();
			}

		}

		private void findNextBlockNums() {
			for (int j = 0; (children[j] != null) && (j < children.length); j++)
				nextBlockNums[j] = children[j].blockNum;
		}

		private int getLastBlockNum() {
			int index = 0;
			for (int i = 0; nextBlockNums[i] != 0 && i < nextBlockNums.length; i++)
				index = nextBlockNums[i];
			return index;
		}

		private void traverse() throws IOException {
			int i;
			for (i = 0; i < numOfValues; i++) {
				if (!isLeaf) {
					children[i].diskRead();
					children[i].traverse();
				}
				System.out.println("|" + values[i] + " | " + keys[i] + "|");
			}
			if (!isLeaf) {
				children[i].diskRead();
				children[i].traverse();
			}
		}

		private void display(T k) throws IOException {
			int i = 0;
			for (i = 0; i < numOfValues; i++) {
				if (k.toString().equalsIgnoreCase(values[i].toString().substring(0, k.toString().length())))
					System.out.println(values[i] + " " + keys[i]);
				if (!isLeaf) {
					children[i].diskRead();
					children[i].display(k);
				}
			}

			if (!isLeaf) {
				children[i].diskRead();
				children[i].display(k);
			}
		}

		private void displayAllDataFromKey(T k) throws IOException {
			int i = 0;
			for (i = 0; i < numOfValues; i++) {
				if (keys[i].toString().toLowerCase().indexOf(k.toString().toLowerCase()) >= 0)
					System.out.println("|" + values[i] + "|");
				if (!isLeaf) {
					children[i].diskRead();
					children[i].displayAllDataFromKey(k);
				}
			}

			if (!isLeaf) {
				children[i].diskRead();
				children[i].displayAllDataFromKey(k);
			}
		}

		private boolean containsVal(T k) throws IOException {
			return search(k) != null;
		}

		private boolean containsKey(T u) throws IOException {
			return searchKey(u) != null;
		}

		private Node<T> search(T k) throws IOException {
			int i = 0;
			while ((i < numOfValues) && cmp.compare(k, values[i]) > 0) {
				i++;
			}
			if ((i < numOfValues) && (cmp.compare(k, values[i]) == 0)) {
				return this;
			} else if (isLeaf) {
				return null;
			} else {
				children[i].diskRead();
				return children[i].search(k);
			}
		}

		private Node<T> searchKey(T u) throws IOException {
			int i = 0;

			while ((i < numOfValues) && cmp.compare(u, keys[i]) > 0)
				i++;
			if ((i < numOfValues) && (cmp.compare(u, keys[i]) == 0))
				return this;
			else if (isLeaf)
				return null;
			else {
				children[i].diskRead();
				return children[i].searchKey(u);
			}
		}

		private Node<T> searchSeq(T k) throws IOException {
			int i = 0;

			while ((i < numOfValues) && (k.toString().compareToIgnoreCase(values[i].toString()) > 0))
				i++;
			if ((i < numOfValues)
					&& (k.toString().equalsIgnoreCase(values[i].toString().substring(0, k.toString().length()))))
				return this;
			else if (isLeaf)
				return null;
			else {
				children[i].diskRead();
				return children[i].searchSeq(k);
			}
		}

		/**
		 * 如果节点非空,进行插入判断,不进行当前节点的分裂
		 * 
		 * @param k
		 * @param u
		 * @throws IOException
		 */
		private void insertNonFull(T k, T u) throws IOException {
			int i = numOfValues - 1;
			// 如果是叶节点,直接进行插入,之前数据都是已排序数据,可以考虑使用二分插入加快速度
			if (isLeaf) {
				while ((i >= 0) && (cmp.compare(values[i], k) > 0)) {
					values[i + 1] = values[i];
					keys[i + 1] = keys[i];
					i--;
				}
				values[i + 1] = k;
				keys[i + 1] = u;
				numOfValues++;
				diskWrite();
			} else {
				while ((i >= 0) && (cmp.compare(values[i], k) > 0)) {
					i--;
				}
				i++;
				children[i].diskRead();
				if (children[i].numOfValues == maxValues) {
					this.splitChild(i, children[i]);
					if (cmp.compare(values[i], k) < 0) {
						i++;
					}
				}
				children[i].insertNonFull(k, u);
			}
		}

		/**
		 * 从某个节点下标开始对原始节点进行分裂
		 * 
		 * @param i
		 * @param thisNode
		 * @throws IOException
		 */
		private void splitChild(int i, Node<T> thisNode) throws IOException {
			// 新的分裂后的节点
			Node<T> newNode = new Node<>(thisNode.degree, thisNode.isLeaf, numOfBlock++);
			// 一个度数为M的B树,包含m-1个关键字,度数为m说明除叶节点和根节点外,其余节点最多包含m个孩子节点,包含m-1个关键字
			newNode.numOfValues = order - 1;
			// 交换文件块
			if (thisNode.blockNum == 0) {
				long newPosition = thisNode.position;
				int newBlockNum = thisNode.blockNum;
				thisNode.blockNum = blockNum;
				thisNode.position = position;
				blockNum = newBlockNum;
				position = newPosition;
			}
			/**
			 * 对应 m阶:最少有m-1个关键字,至多2*m-1个<br/>
			 * 此时m-1即为关键字总数的一半往前一个位置
			 */
			for (int j = 0; j < (order - 1); j++) {
				newNode.values[j] = thisNode.values[j + order];
				newNode.keys[j] = thisNode.keys[j + order];
				thisNode.values[j + order] = null;
				thisNode.keys[j + order] = null;
			}
			// 如果待分裂节点不是叶子节点,复制子节点到新节点上
			// m阶:具有的子节点数量为m
			if (!thisNode.isLeaf) {
				for (int j = 0; j < order; j++) {
					newNode.children[j] = thisNode.children[j + order];
					thisNode.children[j + order] = null;
				}
			}
			thisNode.numOfValues = order - 1;
			for (int j = numOfValues; j >= i + 1; j--) {
				children[j + 1] = children[j];
			}
			children[i + 1] = newNode;
			for (int j = numOfValues - 1; j >= i; j--) {
				values[j + 1] = values[j];
				keys[j + 1] = keys[j];
			}
			values[i] = thisNode.values[order - 1];
			keys[i] = thisNode.keys[order - 1];
			thisNode.values[order - 1] = null;
			thisNode.keys[order - 1] = null;
			numOfValues++;
			diskWrite();
			thisNode.diskWrite();
			newNode.diskWrite();
		}

		private int findKey(T k) {
			int index = 0;

			while ((index < numOfValues) && (((cmp.compare(k, values[index]) > 0)
					|| (k.toString().compareToIgnoreCase(values[index].toString()) > 0))))
				index++;
			return index;
		}

		private T remove(T k) throws IOException {
			T removed = null;
			int index = this.findKey(k);

			if ((index < numOfValues) && (((cmp.compare(values[index], k) == 0) || (k.toString()
					.equalsIgnoreCase(values[index].toString().substring(0, k.toString().length())))))) {
				if (isLeaf)
					removed = this.removeFromLeaf(index);
				else
					removed = this.removeFromNonLeaf(index);
			} else {
				if (children[index].numOfValues < order)
					ensure(index);
				if ((index == numOfValues) && (index > numOfValues)) {
					children[index - 1].diskRead();
					children[index - 1].remove(k);
				} else {
					children[index].diskRead();
					children[index].remove(k);
				}
			}
			return removed;
		}

		private T removeFromLeaf(int i) throws IOException {
			T removed = values[i];

			for (int j = i + 1; j < numOfValues; ++j) {
				values[j - 1] = values[j];
				keys[j - 1] = keys[j];
			}
			values[numOfValues - 1] = null;
			keys[numOfValues - 1] = null;
			numOfValues--;
			diskWrite();
			return removed;
		}

		private T removeFromNonLeaf(int i) throws IOException {
			T removed = values[i];
			Node<T> child = children[i];
			Node<T> sibling = children[i + 1];
			child.diskRead();
			sibling.diskRead();
			if (child.numOfValues >= order) {
				T p = getPredecessor(i);
				values[i] = p;
				child.remove(p);
			} else if (sibling.numOfValues >= order) {
				T s = getSuccessor(i);
				values[i] = s;
				sibling.remove(s);
			} else {
				combine(i);
				child.remove(removed);
			}
			return removed;
		}

		private void ensure(int i) throws IOException {
			if ((i != 0) && (children[i - 1].numOfValues >= order))
				retrieveFromPrevious(i);
			else if ((i != numOfValues) && (children[i + 1].numOfValues >= order))
				retrieveFromNext(i);
			else {
				if (i != numOfValues)
					combine(i);
				else
					combine(i - 1);
			}
		}

		private void combine(int i) throws IOException {
			Node<T> child = children[i];
			Node<T> sibling = children[i + 1];
			child.values[order - 1] = values[i];
			child.keys[order - 1] = keys[i];
			child.diskRead();
			sibling.diskRead();

			for (int j = 0; j < sibling.numOfValues; ++j) {
				child.values[j + order] = sibling.values[j];
				child.keys[j + order] = sibling.keys[j];
			}
			child.numOfValues += sibling.numOfValues + 1;

			if (!child.isLeaf)
				for (int j = 0; j <= sibling.numOfValues; ++j)
					child.children[j + order] = sibling.children[j];

			for (int j = i + 1; j < numOfValues; ++j) {
				values[j - 1] = values[j];
				keys[j - 1] = keys[j];
				children[j] = children[j + 1];
			}
			values[numOfValues - 1] = null;
			keys[numOfValues - 1] = null;
			children[numOfValues] = null;
			numOfValues--;

			diskWrite();
			child.diskWrite();
			sibling.free();
		}

		private T getPredecessor(int i) throws IOException {
			Node<T> current = children[i];
			current.diskRead();

			while (!current.isLeaf)
				current = current.children[current.numOfValues];
			return current.values[current.numOfValues - 1];
		}

		private T getSuccessor(int i) throws IOException {
			Node<T> current = children[i + 1];
			current.diskRead();

			while (!current.isLeaf)
				current = current.children[0];
			return current.values[0];
		}

		private void retrieveFromPrevious(int i) throws IOException {
			Node<T> child = children[i];
			Node<T> sibling = children[i - 1];

			child.diskRead();
			sibling.diskRead();

			for (int j = child.numOfValues - 1; j >= 0; --j) {
				child.values[j + 1] = child.values[j];
				child.keys[j + 1] = child.keys[j];
			}

			if (!child.isLeaf)
				for (int j = child.numOfValues; j >= 0; --j)
					child.children[j + 1] = child.children[j];

			child.values[0] = values[i - 1];
			child.keys[0] = keys[i - 1];

			if (!isLeaf)
				child.children[0] = sibling.children[sibling.numOfValues];

			values[i - 1] = sibling.values[sibling.numOfValues - 1];
			keys[i - 1] = sibling.keys[sibling.numOfValues - 1];

			child.numOfValues++;
			sibling.numOfValues--;

			diskWrite();
			child.diskWrite();
			sibling.diskWrite();
		}

		private void retrieveFromNext(int i) throws IOException {
			Node<T> child = children[i];
			Node<T> sibling = children[i + 1];

			child.diskRead();
			sibling.diskRead();

			child.values[child.numOfValues] = values[i];
			child.keys[child.numOfValues] = keys[i];
			values[i] = sibling.values[0];
			keys[i] = sibling.keys[0];

			if (!child.isLeaf)
				child.children[child.numOfValues] = sibling.children[0];

			for (int j = 1; j < sibling.numOfValues; ++j) {
				sibling.values[j - 1] = sibling.values[j];
				sibling.keys[j - 1] = sibling.keys[j];
			}
			sibling.values[sibling.numOfValues - 1] = null;
			sibling.keys[sibling.numOfValues - 1] = null;

			if (!sibling.isLeaf)
				for (int j = 1; j <= sibling.numOfValues; ++j)
					sibling.children[j - 1] = sibling.children[j];
			sibling.children[sibling.numOfValues] = null;

			child.numOfValues++;
			sibling.numOfValues--;

			diskWrite();
			child.diskWrite();
			sibling.diskWrite();
		}
	}
}
