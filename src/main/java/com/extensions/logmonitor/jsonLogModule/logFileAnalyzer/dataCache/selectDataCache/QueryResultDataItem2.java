package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.javastack.kvstore.holders.DataHolder;

import com.extensions.logmonitor.util.GenericsUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */

public class QueryResultDataItem2 extends DataHolder<QueryResultDataItem2> {

	private static final byte STRING = 1;
	private static final byte INTEGER = 2;
	private static final byte DOUBLE = 0;
	private static final byte LONG = 0;
	private static final byte BOOLEAN = 0;
	private long recordId;
	private String fileName;
	private String offset;
	private Map<String, Object> queryResult;
	private Long groupId;

	public QueryResultDataItem2(long recordId, int querySize) {
		this.queryResult = new LinkedHashMap<>(querySize);
		this.recordId = recordId;
	}

	/**
	 * 
	 */
	public QueryResultDataItem2() {
	}

	public Long getGroupId() {
		return groupId;
	}

	public QueryResultDataItem2 setGroupId(Long groupId) {
		this.groupId = groupId;
		return this;
	}

	public String getFileName() {
		return fileName;
	}

	public QueryResultDataItem2 setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}

	public Long[] getOffset() {
		String[] split = this.offset.split("_");
		return new Long[] { Long.parseLong(split[0]), Long.parseLong(split[1]) };
	}

	public QueryResultDataItem2 setOffset(long start, long end) {
		this.offset = String.format("%s_%s", start, end);
		return this;
	}

	public QueryResultDataItem2 putQueyrResult(String showName, Object value) {
		this.queryResult.put(showName, value);
		return this;
	}

	public Map<String, Object> getQueryResult() {
		return this.queryResult;
	}

	public long getRecordId() {
		return this.recordId;
	}

	@Override
	public String toString() {
		return "QueryResultDataItem [recordId=" + recordId + ", fileName=" + fileName + ", offset=" + offset
				+ ", queryResult=" + queryResult + ", groupId=" + groupId + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((offset == null) ? 0 : offset.hashCode());
		result = prime * result + ((queryResult == null) ? 0 : queryResult.hashCode());
		result = prime * result + (int) (recordId ^ (recordId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryResultDataItem2 other = (QueryResultDataItem2) obj;
		if (fileName == null) {
			if (other.fileName != null)
				return false;
		} else if (!fileName.equals(other.fileName))
			return false;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (offset == null) {
			if (other.offset != null)
				return false;
		} else if (!offset.equals(other.offset))
			return false;
		if (queryResult == null) {
			if (other.queryResult != null)
				return false;
		} else if (!queryResult.equals(other.queryResult))
			return false;
		if (recordId != other.recordId)
			return false;
		return true;
	}

	@Override
	public int compareTo(QueryResultDataItem2 another) {
		return 0;
	}

	@Override
	public int byteLength() {
		int length = Long.SIZE + Integer.SIZE + this.fileName.getBytes().length + Integer.SIZE;
		if (GenericsUtils.notNullAndEmpty(this.queryResult)) {
			for (Map.Entry<String, Object> entry : this.queryResult.entrySet()) {
				length += (entry.getKey().getBytes().length + Integer.SIZE);
				length += Byte.SIZE;
				Object value = entry.getValue();
				if (value instanceof String) {
					length += Integer.SIZE;
					length += (((String) value).getBytes().length);
				} else if (value instanceof Integer) {
					length += Integer.SIZE;
				} else if (value instanceof Double) {
					length += Double.SIZE;
				} else if (value instanceof Long) {
					length += Long.SIZE;
				} else if (value instanceof Boolean) {
					length += Byte.SIZE;
				}
			}
		}
		if (this.groupId != null) {
			length += Long.SIZE;
		}
		return length;
	}

	@Override
	public void serialize(ByteBuffer buf) {
		buf.putLong(this.recordId);
		buf.putInt(this.fileName.getBytes().length);
		buf.put(this.fileName.getBytes());
		// buf.put(this.offset.getBytes());
		if (GenericsUtils.notNullAndEmpty(this.queryResult)) {
			buf.putInt(this.queryResult.size());
			for (Map.Entry<String, Object> entry : this.queryResult.entrySet()) {
				buf.putInt(entry.getKey().getBytes().length);
				buf.put(entry.getKey().getBytes());
				Object value = entry.getValue();
				if (value instanceof String) {
					buf.put((byte) 1);
					buf.putInt(((String) value).getBytes().length);
					buf.put(((String) value).getBytes());
				} else if (value instanceof Integer) {
					buf.put((byte) 2);
					buf.putInt((Integer) value);
				} else if (value instanceof Double) {
					buf.put((byte) 3);
					buf.putDouble((Double) value);
				} else if (value instanceof Long) {
					buf.put((byte) 4);
					buf.putDouble((Long) value);
				} else if (value instanceof Boolean) {
					buf.put((byte) 5);
					buf.put(((boolean) value) ? (byte) 1 : (byte) 0);
				}
			}
		} else {
			buf.putInt(0);
		}
		if (this.groupId != null) {
			buf.putLong(this.groupId);
		}
	}

	@Override
	public QueryResultDataItem2 deserialize(ByteBuffer buf) {
		QueryResultDataItem2 ret = new QueryResultDataItem2();
		ret.recordId = buf.getLong();
		int fileNameSize = buf.getInt();
		ByteBuffer byteBuffer = buf.get(new byte[fileNameSize]);
		ret.fileName = new String(byteBuffer.array());
		int sizeOfValue = buf.getInt();
		if (sizeOfValue != 0) {
			ret.queryResult = new HashMap<>(sizeOfValue);
			for (int i = 0; i < sizeOfValue; i++) {
				String key = null;
				int keySize = buf.getInt();
				key = getStr(buf, keySize);
				byte type = buf.get();
				Object value = null;
				if (type == STRING) {
					int strSize = buf.getInt();
					value = getStr(buf, strSize);
				} else if (type == INTEGER) {
					value = buf.getInt();
				} else if (type == DOUBLE) {
					value = buf.getDouble();
				} else if (type == LONG) {
					value = buf.getLong();
				} else if (type == BOOLEAN) {
					value = (buf.get() == 0 ? false : true);
				}
				ret.queryResult.put(key, value);
			}
		}
		ret.groupId = buf.getLong();
		return null;
	}

	/**
	 * @param buf
	 * @param keySize
	 * @return
	 */
	private String getStr(ByteBuffer buf, int keySize) {
		ByteBuffer byteBuffer = buf.get(new byte[keySize]);
		return new String(byteBuffer.array());
	}

}
