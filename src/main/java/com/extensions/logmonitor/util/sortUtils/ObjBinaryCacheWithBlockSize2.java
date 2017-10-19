package com.extensions.logmonitor.util.sortUtils;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.time.StopWatch;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.DataSizeCountable;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.SeriAndDeser;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.util.GenericsUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月11日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class ObjBinaryCacheWithBlockSize2<T extends DataSizeCountable> {

	private int defaultBlockSize = 20;
	private List<BlockData<T>> blocks = new ArrayList<>(defaultBlockSize);
	private List<BlockData<T>> afterSyncBlocks = new ArrayList<>();
	private SeriAndDeser<T> keySeriAndDeser;
	private int countIndex = 0;
	private FileStore orderResultStore;
	private File file;
	private Class<T> dataClassType;
	static StopWatch stopwatch = new StopWatch();
	private AtomicInteger blockIndex = new AtomicInteger(0);

	public ObjBinaryCacheWithBlockSize2(String filePath, SeriAndDeser<T> keySeriAndDeser, Class<T> dataClassType) {
		this.keySeriAndDeser = keySeriAndDeser;
		this.dataClassType = dataClassType;
		this.file = new File(filePath);
		System.out.println("this.file:" + this.file.getAbsolutePath());
		for (int i = 0; i < defaultBlockSize; i++) {
			FileStore fileStore = new FileStore(
					new File(this.file, "objBinaryTemp_" + blockIndex.getAndIncrement() + ".bina"));
			if (!fileStore.open()) {
				System.out.println("open file failure!!!!");
				throw new IllegalStateException();
			}
			blocks.add(new BlockData<T>(fileStore, this.keySeriAndDeser, dataClassType));
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
			System.out.println("sync blockIndex:" + index + "\tuse time:" + stopwatch.getSplitTime());
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
		BlockData<T> blockData = new BlockData<T>(fileStore, this.keySeriAndDeser, dataClassType);
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
		List<Integer> computeBlocks = new ArrayList<>();
		for (int i = 0; i < this.blocks.size(); i++) {
			BlockData<T> blockData = blocks.get(i);
			if (blockData.totalSize == 0) {
				computeBlocks.add(i);
			} else {
				blocks.get(i).sync();
				blocks.get(i).clear();
				stopwatch.split();
				System.out.println("triggerOver blockIndex:" + i + "\t totalSize:" + blocks.get(i).totalSize
						+ " \tuse time:" + stopwatch.getSplitTime());
				this.afterSyncBlocks.add(blockData);
			}
		}
		for (int i = 0; i < computeBlocks.size(); i++) {
			Integer removeIndex = computeBlocks.get(i);
			BlockData<T> blockData = this.blocks.get(removeIndex);
			blockData.clear();
			this.blocks.remove(blockData);
			System.out.println("blockIndex " + computeBlocks.get(i) + " is empty , remove it!!!");
		}
		this.blocks.clear();
		this.blocks.addAll(afterSyncBlocks);
		this.afterSyncBlocks.clear();
	}

	public void doSort() {
		this.doSort(null, null);
	}

	public List<T> doSort(Integer offset, Integer size) {
		System.out.print("doSort------");
		stopwatch.split();
		int readSize = this.countIndex;
		if (offset != null && size != null) {
			readSize = offset + size;
			if (readSize > this.countIndex) {
				readSize = this.countIndex;
			}
		}
		List<T> afterSort = new ArrayList<>(readSize);
		HeapReadQueue hrq = new HeapReadQueue();
		int i = 0;
		for (; i < readSize; i++) {
			T readData = hrq.readNextData();
			if (readData != null) {
				if (offset != null && i >= offset) {
					afterSort.add(readData);
				} else {
					afterSort.add(readData);
				}
			} else {
				break;
			}
		}
		stopwatch.split();
		System.out.println("sort over!!! , sort data's size:" + readSize + ",and useTime:" + stopwatch.getSplitTime());
		for (T dataItem : afterSort) {
			System.out.println(dataItem);
		}
		System.out.println();
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

		public BlockData(FileStore fileStore, SeriAndDeser<T> seriAndDes, Class<T> dataClassType) {
			this.datas = GenericsUtils.createArray(dataClassType, batchSize);
			this.seriAndDes = seriAndDes;
			this.buff = ByteBuffer.allocate(1024);
			this.fileStore = fileStore;
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

		int batchSize = 100000;

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

		ObjBinaryCacheWithBlockSize2<OrderByDataItemWithObj> cache = new ObjBinaryCacheWithBlockSize2<>(
				"/Users/rezar/Desktop/", keySeriAndDeser, OrderByDataItemWithObj.class);
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
