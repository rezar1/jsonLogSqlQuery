package com.extensions.logmonitor.util.sortUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.DataSizeCountable;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.SeriAndDeser;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItem;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月11日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class TestStoreClass {

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
		FileStore fileStore = new FileStore("/Users/rezar/Desktop/testStore.bin");
		fileStore.open();
		List<BlockData<OrderByDataItemWithObj>> blocks = new ArrayList<>(10);
		for (int i = 0; i < 10; i++) {
			blocks.add(new BlockData<OrderByDataItemWithObj>(keySeriAndDeser));
		}
		List<OrderByDataItem> items = new ArrayList<>();
		for (int i = 0; i < 1000000; i++) {
			OrderByDataItemWithObj item = new OrderByDataItemWithObj();
			item.initValueSize(3);
			item.setRecordId(i);
			item.addCacheData(0, "fjdlsajfugfdjlkgjlejrknjnvhdfajdsjfhgjjkdosiafiodsafnncxvndfstring:" + i);
			item.addCacheData(1, i * 1000);
			item.addCacheData(2, i % 2 == 0 ? true : false);
			items.add(item);
			int index = i % blocks.size();
			blocks.get(index).addData(item);
		}
		long time1, time2;
		time1 = System.currentTimeMillis();
		for (int i = 0; i < blocks.size(); i++) {
			BlockData<OrderByDataItemWithObj> blockData = blocks.get(i);
			ByteBuffer buf = null;
			do {
				buf = blockData.createByteBuffer();
				fileStore.set(buf);
			} while (!blockData.isOver);
			fileStore.sync();
		}
		time2 = System.currentTimeMillis();
		System.out.println("ser use time:" + (time2 - time1));
		System.out.println("sync over!!!");
		fileStore.changeToRead();
		// 测试序列化回来
		ByteBuffer tempBuf = null;
		time1 = System.currentTimeMillis();
		while ((tempBuf = fileStore.read()) != null) {
			OrderByDataItemWithObj deserialize = keySeriAndDeser.deserialize(tempBuf);
			System.out.println(deserialize);
		}
		time2 = System.currentTimeMillis();
		System.out.println("des use time:" + (time2 - time1));
		Thread.sleep(10000000);
	}

	private static class BlockData<T extends DataSizeCountable> {
		private ByteBuffer buff;
		private int size;
		private List<T> datas;
		private SeriAndDeser<T> seriAndDes;
		int visitIndex;
		private boolean isOver;

		public BlockData(SeriAndDeser<T> seriAndDes) {
			this.datas = new LinkedList<>();
			this.seriAndDes = seriAndDes;
			this.buff = ByteBuffer.allocate(512);
		}

		public void addData(T data) {
			this.datas.add(data);
			this.size += data.sizeOfData();
		}

		public ByteBuffer createByteBuffer() {
			this.buff.clear();
//			System.out.println("dataSize:" + this.datas.size() + "\tvisitIndex:" + this.visitIndex);
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
				// System.out.println("fill data:" + next);
				this.visitIndex++;
			}
			if (this.visitIndex >= this.datas.size()) {
				isOver = true;
			}
			this.buff.flip();
			return this.buff;
		}

	}

}
