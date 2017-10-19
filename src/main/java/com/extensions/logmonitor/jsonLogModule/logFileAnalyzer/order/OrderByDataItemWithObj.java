package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order;

import java.nio.ByteBuffer;
import java.util.Arrays;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.DataSizeCountable;

/****
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class OrderByDataItemWithObj implements OrderByDataItem, DataSizeCountable {

	public static final byte STRING = 1;
	public static final byte INTEGER = 2;
	public static final byte DOUBLE = 3;
	public static final byte LONG = 4;
	public static final byte BOOLEAN = 5;
	public static final byte FLOAT = 6;

	private long recordId;
	private Object[] cacheDatas;

	@Override
	public int sizeOfData() {
		int size = 0;
		size += 8;
		size += 4;
		for (Object obj : cacheDatas) {
			size += 1;
			if (obj instanceof String) {
				size += 4; // 记录长度
				size += ((String) obj).getBytes().length;// 记录实际数据大小
			} else if (obj instanceof Integer) {
				size += 4;
			} else if (obj instanceof Double) {
				size += 8;
			} else if (obj instanceof Long) {
				size += 8;
			} else if (obj instanceof Boolean) {
				size += 1;
			}
		}
		return size;
	}

	public void serialize(ByteBuffer buff) {
		buff.putLong(this.recordId);
		buff.putInt(this.cacheDatas.length);
		for (Object obj : cacheDatas) {
			if (obj instanceof String) {
				buff.put(STRING);
				String objStr = (String) obj;
				byte[] bytes = objStr.getBytes();
				buff.putInt(bytes.length);
				buff.put(bytes);
			} else if (obj instanceof Integer) {
				buff.put(INTEGER);
				buff.putInt((Integer) obj);
			} else if (obj instanceof Double) {
				buff.put(DOUBLE);
				buff.putDouble((Double) obj);
			} else if (obj instanceof Long) {
				buff.put(LONG);
				buff.putLong((Long) obj);
			} else if (obj instanceof Boolean) {
				buff.put(BOOLEAN);
				buff.put((Boolean) obj ? (byte) 1 : (byte) -1);
			}
		}
	}

	public static OrderByDataItemWithObj deserialize(ByteBuffer buf) {
		Long recordId = buf.getLong();
		int dataSize = buf.getInt();
		Object[] dataArray = new Object[dataSize];
		for (int i = 0; i < dataSize; i++) {
			Object dataItem = null;
			byte type = buf.get();
			switch (type) {
			case STRING:
				int strSize = buf.getInt();
				byte[] temp = new byte[strSize];
				buf.get(temp);
				dataItem = new String(temp);
				break;
			case INTEGER:
				dataItem = buf.getInt();
				break;
			case DOUBLE:
				dataItem = buf.getDouble();
				break;
			case LONG:
				dataItem = buf.getLong();
				break;
			case FLOAT:
				dataItem = buf.getFloat();
				break;
			case BOOLEAN:
				dataItem = buf.get() == 1 ? true : false;
				break;
			default:
				break;
			}
			dataArray[i] = dataItem;
		}
		OrderByDataItemWithObj obiwo = new OrderByDataItemWithObj();
		obiwo.setRecordId(recordId);
		obiwo.cacheDatas = dataArray;
		return obiwo;
	}

	@Override
	public void addCacheData(int index, Object pathFieldValue) {
		this.cacheDatas[index] = pathFieldValue;
	}

	@Override
	public long getRecordId() {
		return this.recordId;
	}

	@Override
	public String toString() {
		return "OrderByDataItemWithObj [recordId=" + recordId + ", cacheDatas=" + Arrays.toString(cacheDatas) + "]";
	}

	@Override
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}

	@Override
	public Object[] getOrderByValues() {
		return this.cacheDatas;
	}

	@Override
	public void initValueSize(int expectSize) {
		this.cacheDatas = new Object[expectSize];
	}

}
