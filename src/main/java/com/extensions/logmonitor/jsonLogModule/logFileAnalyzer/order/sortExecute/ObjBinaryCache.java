package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.sortExecute;

import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
public class ObjBinaryCache<T extends DataSizeCountable> {

	private int defaultBlockSize = 40;
	private List<BlockData<T>> blocks = new ArrayList<>(defaultBlockSize);
	private SeriAndDeser<T> keySeriAndDeser;
	private int countIndex = 0;
	static StopWatch stopwatch = new StopWatch();

	public ObjBinaryCache(String filePath, SeriAndDeser<T> keySeriAndDeser, Comparator<T> comparator,
			Class<T> dataClassType) {
		this.keySeriAndDeser = keySeriAndDeser;
		File file = new File(filePath);
		for (int i = 0; i < defaultBlockSize; i++) {
			FileStore fileStore = new FileStore(new File(file, "objBinaryTemp_" + i + ".bina"));
			if (!fileStore.open()) {
				System.out.println("open file failure!!!!");
				throw new IllegalStateException();
			}
			blocks.add(new BlockData<T>(executor, fileStore, this.keySeriAndDeser, comparator, dataClassType));
		}
		stopwatch.start();
	}

	public void cacheData(T data) {
		final int index = (countIndex++) % this.blocks.size();
		BlockData<T> blockData = blocks.get(index);
		blockData.addData(data);
		if (blockData.isFull()) {
			blockData.sync();
			stopwatch.split();
			System.out.println("sync blockIndex:" + index + "use time:" + stopwatch.getSplitTime());
		}
	}

	public void triggerOver() {
		for (int i = 0; i < this.blocks.size(); i++) {
			blocks.get(i).sync();
			blocks.get(i).clear();
			stopwatch.split();
			System.out.println("blockIndex:" + i + "use time:" + stopwatch.getSplitTime());
		}
	}

	ExecutorService executor = Executors.newFixedThreadPool(this.blocks.size() / 4);

	public void doSort() {
		ExecutorService executor = Executors.newFixedThreadPool(this.blocks.size() / 4);
		for (final BlockData<T> blockData : this.blocks) {
			executor.execute(new Runnable() {
				@Override
				public void run() {
					blockData.doBlockSort();
				}
			});
		}
		executor.shutdown();
		while (!executor.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stopwatch.split();
		System.out.println("sync all and sort use time:" + stopwatch.getSplitTime());
		System.out.print("=======*********=======");
		BlockData<T> blockData = this.blocks.get(0);
		blockData.readData();
	}

	private static class BlockData<T extends DataSizeCountable> {
		private ByteBuffer buff;
		private List<T> datas;
		private Class<T> dataClassType;
		private SeriAndDeser<T> seriAndDes;
		int visitIndex;
		private boolean isOver;
		private FileStore fileStore;
		private int totalSize;
		private Comparator<T> comparator;

		public BlockData(ExecutorService executor, FileStore fileStore, SeriAndDeser<T> seriAndDes,
				Comparator<T> comparator, Class<T> dataClassType) {
			this.datas = new LinkedList<>();
			this.seriAndDes = seriAndDes;
			this.buff = ByteBuffer.allocate(1024);
			this.fileStore = fileStore;
			this.comparator = comparator;
			this.dataClassType = dataClassType;
		}

		public void clear() {
			this.buff.clear();
			this.datas.clear();
		}

		/**
		 * 
		 */
		public void sync() {
			ByteBuffer buf = null;
			do {
				buf = this.createByteBuffer();
				fileStore.set(buf);
			} while (!this.isOver);
			fileStore.sync();
			this.datas.clear();
			this.visitIndex = 0;
			this.isOver = false;
		}

		int batchSize = 10000;

		/**
		 * @return
		 */
		public boolean isFull() {
			return this.datas.size() >= batchSize;
		}

		public void addData(T data) {
			this.datas.add(data);
			this.totalSize++;
		}

		public void addDataNotCountTotalSize(T data) {
			this.datas.add(data);
		}

		public ByteBuffer createByteBuffer() {
			this.buff.clear();
			for (int i = visitIndex; i < this.datas.size(); i++) {
				T next = this.datas.get(i);
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
			if (this.visitIndex >= this.datas.size()) {
				isOver = true;
			}
			this.buff.flip();
			return this.buff;
		}

		public void doBlockSort() {
			if (this.totalSize == 0) {
				return;
			}
			this.fileStore.changeToRead();
			T[] tempDataBatch = GenericsUtils.createArray(dataClassType, this.totalSize);
			// 测试序列化回来
			ByteBuffer tempBuf = null;
			boolean hasValue = false;
			for (int i = 0; i < this.totalSize; i++) {
				tempBuf = fileStore.read();
				if (tempBuf != null) {
					T deserialize = seriAndDes.deserialize(tempBuf);
					if (deserialize != null) {
						hasValue = true;
					}
					tempDataBatch[i] = deserialize;
				}
			}
			if (!hasValue) {
				return;
			}
			ParallelMergeFileSort.parallelSort(tempDataBatch, this.datas.size(), this.comparator);
			this.fileStore.changeToRead();
			this.isOver = false;
			for (T item : tempDataBatch) {
				this.addDataNotCountTotalSize(item);
			}
			this.sync();
			stopwatch.split();
			System.out.println("doSort blockIndex:" + this + "use time:" + stopwatch.getSplitTime());
		}

		public void readData() {
			this.fileStore.changeToRead();
			ByteBuffer tempBuf = null;
			while ((tempBuf = fileStore.read()) != null) {
				T deserialize = this.seriAndDes.deserialize(tempBuf);
				System.out.println(deserialize);
			}
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

		ObjBinaryCache<OrderByDataItemWithObj> cache = new ObjBinaryCache<>("/Users/rezar/Desktop/", keySeriAndDeser,
				orderTypeComparator, OrderByDataItemWithObj.class);
		for (int i = 0; i < 2000000; i++) {
			OrderByDataItemWithObj item = new OrderByDataItemWithObj();
			item.initValueSize(3);
			item.setRecordId(i);
			item.addCacheData(0, "fjdlsajfugfdjlkgjlejrknjnvhdfajdsjfhgjjkdosiafiodsafnncxvndfstring:" + i);
			item.addCacheData(1, i * 1000);
			item.addCacheData(2, i % 2 == 0 ? true : false);
			cache.cacheData(item);
		}
		cache.triggerOver();
		cache.doSort();
	}

}
