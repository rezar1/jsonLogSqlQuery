package com.extensions.logmonitor.util.sortUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.time.StopWatch;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.DataSizeCountable;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.SeriAndDeser;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderType;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderTypeComparator;
import com.extensions.logmonitor.util.GenericsUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月11日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class ObjBinaryCacheWithBlockSize<T extends DataSizeCountable> {

	private int defaultBlockSize = 20;
	private List<BlockData<T>> blocks = new ArrayList<>(defaultBlockSize);
	private List<BlockData<T>> afterSyncBlocks = new ArrayList<>();
	private SeriAndDeser<T> keySeriAndDeser;
	private int countIndex = 0;
	private FileStore orderResultStore;
	private Comparator<T> comparator;
	private File file;
	private Class<T> dataClassType;
	StopWatch stopwatch = new StopWatch();
	private AtomicInteger blockIndex = new AtomicInteger(0);

	public ObjBinaryCacheWithBlockSize(String filePath, SeriAndDeser<T> keySeriAndDeser, Comparator<T> comparator,
			Class<T> dataClassType) {
		this.keySeriAndDeser = keySeriAndDeser;
		this.dataClassType = dataClassType;
		this.file = new File(filePath);
		this.comparator = comparator;
		for (int i = 0; i < defaultBlockSize; i++) {
			FileStore fileStore = new FileStore(
					new File(this.file, "objBinaryTemp_" + blockIndex.getAndIncrement() + ".bina"));
			if (!fileStore.open()) {
				System.out.println("open file failure!!!!");
				throw new IllegalStateException();
			}
			blocks.add(new BlockData<T>(fileStore, this.keySeriAndDeser, comparator, dataClassType));
		}
		this.orderResultStore = new FileStore(new File(file, "objBinaryOrderResult.bina"));
		if (!orderResultStore.open()) {
			System.out.println("open orderResultStore failure!!!!");
			throw new IllegalStateException();
		}
		stopwatch.start();
	}

	public void cacheData(T data) {
		final int index = (countIndex++) % this.blocks.size();
		BlockData<T> blockData = blocks.get(index);
		if (blockData.isFull()) {
			blockData.sync();
			stopwatch.split();
			// System.out.println("sync blockIndex:" + index + "\tuse time:" +
			// stopwatch.getSplitTime());
			allocateNewBlockData(data);
			afterSyncBlocks.add(this.blocks.remove(index));
		} else {
			blockData.addData(data);
		}
	}

	/**
	 * @param data
	 * @param blockData
	 * @param data
	 */
	private void allocateNewBlockData(T data) {
		FileStore fileStore = new FileStore(
				new File(this.file, "objBinaryTemp_" + this.blockIndex.getAndIncrement() + ".bina"));
		if (!fileStore.open()) {
			System.out.println("open file failure!!!!");
			throw new IllegalStateException();
		}
		BlockData<T> blockData = new BlockData<T>(fileStore, this.keySeriAndDeser, comparator, dataClassType);
		blocks.add(blockData);
		blockData.addData(data);
	}

	public void closeCache() {
		for (BlockData<T> blockData : this.blocks) {
			blockData.fileStore.delete();
		}
		this.orderResultStore.delete();
		this.blocks.clear();
		this.blocks = null;
	}

	public void triggerOver() {
		List<BlockData<T>> computeBlocks = new ArrayList<>();
		for (int i = 0; i < this.blocks.size(); i++) {
			BlockData<T> blockData = blocks.get(i);
			if (blockData.totalSize == 0) {
//				System.out.println("blockDataIndex need remove:" + i);
				computeBlocks.add(blockData);
			} else {
				blocks.get(i).sync();
				blocks.get(i).clear();
				stopwatch.split();
//				System.out.println("triggerOver blockIndex:" + i + "\t totalSize:" + blocks.get(i).totalSize
//						+ " \tuse time:" + stopwatch.getSplitTime());
				this.afterSyncBlocks.add(blockData);
			}
		}
		for (int i = 0; i < computeBlocks.size(); i++) {
			BlockData<T> blockData = computeBlocks.get(i);
			blockData.clear();
			this.blocks.remove(blockData);
//			System.out.println("blockIndex " + blockData + " is empty , remove it!!!");
		}
		this.blocks.clear();
		this.blocks.addAll(afterSyncBlocks);
		this.afterSyncBlocks.clear();
	}

	public void doSort() {
		this.doSort(null, null);
	}

	@SuppressWarnings("unchecked")
	public List<T> doSort(Integer offset, Integer size) {
		System.out.print("doSort------");
		stopwatch.split();
		IdMarkHeapItem<Integer, T>[] datas = GenericsUtils.createArray(IdMarkHeapItem.class, this.blocks.size());
		int readSize = this.countIndex;
		if (offset != null && size != null) {
			readSize = offset + size;
			if (readSize > this.countIndex) {
				readSize = this.countIndex;
			}
		}
		List<T> afterSort = new ArrayList<>(readSize);
		HeapReadQueue hrq = new HeapReadQueue();
		int nullCount = 0;
		int i = 0;
		for (; nullCount < datas.length && i < datas.length;) {
			T readData = hrq.readNextData();
			if (readData != null) {
				datas[i++] = new IdMarkHeapItem<Integer, T>(hrq.currentReadIndex, readData);
			} else {
				nullCount++;
			}
		}
		if (nullCount >= datas.length) {
			if (i != 0) {
				IdMarkHeapItem<Integer, T>[] datas2 = GenericsUtils.createArray(IdMarkHeapItem.class, i);
				System.arraycopy(datas, 0, datas2, 0, i);
				datas = null;
				datas = datas2;
			} else {
				System.out.println("can not read any data from dataBinary file");
				return afterSort;
			}
		}
		Comparator<IdMarkHeapItem<Integer, T>> idMarkComparator = new Comparator<IdMarkHeapItem<Integer, T>>() {
			@Override
			public int compare(IdMarkHeapItem<Integer, T> o1, IdMarkHeapItem<Integer, T> o2) {
				if (o1 == null) {
					return -1;
				}
				if (o2 == null) {
					return 1;
				}
				return comparator.compare(o1.data, o2.data);
			}
		};
		MinHeap<IdMarkHeapItem<Integer, T>> minHeap = new MinHeap<IdMarkHeapItem<Integer, T>>(datas, idMarkComparator);
		minHeap.createHeap();
		i = 0;
		for (; i < readSize; i++) {
			IdMarkHeapItem<Integer, T> idMarkHeapItem = minHeap.takeMin();
			if (offset != null && i >= offset) {
				afterSort.add(idMarkHeapItem.data);
			}
			hrq.resetReadIndex(idMarkHeapItem.IdMark);
			T readData = hrq.readNextData();
			if (readData != null) {
				minHeap.setRoot(new IdMarkHeapItem<Integer, T>(hrq.currentReadIndex, readData));
			} else {
				minHeap.reHeap();
			}
		}
		stopwatch.split();
		System.out.println("sort over!!! , sort data's size:" + readSize + ",and useTime:" + stopwatch.getSplitTime());
		// for (T dataItem : afterSort) {
		// System.out.println(dataItem);
		// }
		// System.out.println();
		return afterSort;
	}

	private class HeapReadQueue {
		private Map<Integer, Boolean> readIndexMark = new HashMap<>();
		private int currentReadIndex;

		HeapReadQueue() {
			for (int i = 0; i < blocks.size(); i++) {
				blocks.get(i).fileStore.changeToRead();
				readIndexMark.put(i, true);
			}
		}

		/**
		 * @param idMark
		 */
		public void resetReadIndex(Integer idMark) {
			if (this.readIndexMark.containsKey(idMark)) {
				this.readIndexMark.put(idMark, true);
			}
		}

		/**
		 * 
		 */
		private void allocateReadIndex() {
			for (int i = 0; i < blocks.size(); i++) {
				readIndexMark.put(i, true);
			}
		}

		public T readNextData() {
			if (readIndexMark.isEmpty()) {
				// System.out.println("data read over!!!");
				return null;
			}
			int readIndex = -1;
			for (Map.Entry<Integer, Boolean> entry : this.readIndexMark.entrySet()) {
				int index = entry.getKey();
				Boolean canRead = entry.getValue();
				if (!canRead) {
					continue;
				} else {
					readIndex = index;
					break;
				}
			}
			if (readIndex == -1) {
				this.allocateReadIndex();
				readIndex = 0;
			}
			this.readIndexMark.put(readIndex, false);
			currentReadIndex = readIndex;
			T readData = blocks.get(readIndex).readData();
			if (readData == null) {
				this.readIndexMark.remove(readIndex);
				// System.out.println("blockData " + readIndex + " is read
				// over!!!" + "\t" + removeRes);
				return this.readNextData();
			}
			return readData;
		}

	}

	private static class BlockData<T extends DataSizeCountable> {
		private ByteBuffer buff;
		private T[] datas;
		private SeriAndDeser<T> seriAndDes;
		int visitIndex;
		private boolean isOver;
		private FileStore fileStore;
		private int totalSize;
		private Comparator<T> comparator;

		public BlockData(FileStore fileStore, SeriAndDeser<T> seriAndDes, Comparator<T> comparator,
				Class<T> dataClassType) {
			this.datas = GenericsUtils.createArray(dataClassType, batchSize);
			this.seriAndDes = seriAndDes;
			this.buff = ByteBuffer.allocate(1024);
			this.fileStore = fileStore;
			this.comparator = comparator;
		}

		public void clear() {
			this.buff.clear();
			this.datas = null;
		}

		/**
		 * 
		 */
		public void sync() {
			if (this.datas == null || this.totalSize == 0) {
				return;
			}
			if (this.totalSize > 1) {
				ParallelMergeFileSort.parallelSort(datas, this.totalSize, comparator);
			}
			ByteBuffer buf = null;
			do {
				buf = this.createByteBuffer();
				fileStore.set(buf);
			} while (!this.isOver);
			fileStore.sync();
			this.datas = null;
			this.visitIndex = 0;
			this.isOver = false;
		}

		int batchSize = 20000;

		/**
		 * @return
		 */
		public boolean isFull() {
			return this.totalSize >= batchSize;
		}

		public void addData(T data) {
			this.datas[totalSize++] = data;
		}

		public ByteBuffer createByteBuffer() {
			this.buff.clear();
			for (int i = visitIndex; i < this.totalSize; i++) {
				T next = this.datas[i];
				int size = next.sizeOfData();
				int remaining = this.buff.remaining();
				if (this.buff.position() == 0 && (size + 4 >= this.buff.capacity())) {
					// 大小不足以接受一条记录
					this.buff = ByteBuffer.allocate(size + 4);
				} else {
					if (remaining <= size + 4) {
						break;
					} else {
						buff.putInt(next.sizeOfData());
						seriAndDes.serialize(next, buff);
					}
				}
				this.visitIndex++;
			}
			if (this.visitIndex >= this.totalSize) {
				isOver = true;
			}
			this.buff.flip();
			return this.buff;
		}

		public T readData() {
			ByteBuffer tempBuf = null;
			if ((tempBuf = fileStore.read()) != null) {
				T deserialize = this.seriAndDes.deserialize(tempBuf);
				return deserialize;
			}
			return null;
		}

	}

	public static class IdMarkHeapItem<I, D> {
		public final I IdMark;
		public final D data;

		public IdMarkHeapItem(I idMark, D data) {
			this.IdMark = idMark;
			this.data = data;
		}
	}

	public static void main(String[] args) throws InterruptedException {
		SeriAndDeser<OrderByDataItemWithObj> keySeriAndDeser = new SeriAndDeser<OrderByDataItemWithObj>() {
			@Override
			public void serialize(OrderByDataItemWithObj obj, ByteBuffer buff) {
				obj.serialize(buff);
			}

			@Override
			public OrderByDataItemWithObj deserialize(ByteBuffer buf) {
				return OrderByDataItemWithObj.deserialize(buf);
			}
		};
		List<OrderType> orderTypes = Arrays.asList(OrderType.ASC, OrderType.DESC, OrderType.ASC);
		OrderTypeComparator orderTypeComparator = new OrderTypeComparator(orderTypes);

		ObjBinaryCacheWithBlockSize<OrderByDataItemWithObj> cache = new ObjBinaryCacheWithBlockSize<>(
				"/Users/rezar/Desktop/", keySeriAndDeser, orderTypeComparator, OrderByDataItemWithObj.class);
		Random random = new Random();
		for (int i = 0; i < 10000001; i++) {
			OrderByDataItemWithObj item = new OrderByDataItemWithObj();
			item.initValueSize(3);
			item.setRecordId(i);
			item.addCacheData(0, i);
			item.addCacheData(1, random.nextInt(1000) + "_index:" + i);
			item.addCacheData(2, random.nextInt(100000) % 2 == 0 ? true : false);
			cache.cacheData(item);
		}
		cache.triggerOver();
		cache.doSort(10, 1000);
	}

}
