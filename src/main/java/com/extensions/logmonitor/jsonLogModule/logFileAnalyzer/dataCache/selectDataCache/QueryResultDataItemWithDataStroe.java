package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache;

import java.util.LinkedHashMap;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.DataSizeCountable;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */

public class QueryResultDataItemWithDataStroe implements DataSizeCountable {

	public static final byte STRING = 1;
	public static final byte INTEGER = 2;
	public static final byte DOUBLE = 3;
	public static final byte LONG = 4;
	public static final byte BOOLEAN = 5;
	public static final byte FLOAT = 6;

	private long recordId;
	private long offset;
	private Map<String, Object> queryResult;
	private Long groupId;

	public QueryResultDataItemWithDataStroe(long recordId, int querySize) {
		this.queryResult = new LinkedHashMap<>(querySize);
		this.recordId = recordId;
	}

	@Override
	public int sizeOfData() {
		int size = 0;
		size += 8;
		size += 4;// this.queryResult.size();
		for (Map.Entry<String, Object> entry : this.queryResult.entrySet()) {
			size += 4;
			size += entry.getKey().getBytes().length;
			Object value = entry.getValue();
			size += 1;
			if (value instanceof String) {
				size += 4;
				size += value.toString().getBytes().length;
			} else if (value instanceof Integer) {
				size += 4;
			} else if (value instanceof Float) {
				size += 4;
			} else if (value instanceof Long) {
				size += 8;
			} else if (value instanceof Boolean) {
				size += 1;
			} else if (value instanceof DataSizeCountable) {
				size += ((DataSizeCountable) value).sizeOfData();
			}
		}
		size += 8;
		return size;
	}

	public Long getGroupId() {
		return groupId;
	}

	public QueryResultDataItemWithDataStroe setGroupId(Long groupId) {
		this.groupId = groupId;
		return this;
	}

	public long getOffset() {
		return this.offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public QueryResultDataItemWithDataStroe putQueyrResult(String showName, Object value) {
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
		return "QueryResultDataItemWithDataStroe [recordId=" + recordId + ", offset=" + offset + ", queryResult="
				+ queryResult + ", groupId=" + groupId + "]";
	}

}
