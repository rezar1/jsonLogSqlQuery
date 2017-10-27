package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.time.StopWatch;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.BplusTree;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.DataSizeCountable;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.SeriAndDeser;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.utils.StringDescAndSer;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache.SingleOrderByDataCache;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group.GroupFilter;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.util.GenericsUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月17日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class OutterQueryResultDataCache implements QueryResultDataCache {

	private BplusTree<LongKey, QueryResultDataItem> bplusTreeCache;

	private AtomicLong recordCount = new AtomicLong(0);

	private SingleOrderByDataCache orderByDataCache;

	private boolean distinct;

	public static void main(String[] args) {
		Random random = new Random();
		StopWatch watch = new StopWatch();
		watch.start();
		OutterQueryResultDataCache cache = new OutterQueryResultDataCache();
		for (int i = 0; i < 10000000; i++) {
			QueryResultDataItem cacheData = new QueryResultDataItem(i, 3);
			cacheData.putQueyrResult("name" + i, i);
			cacheData.putQueyrResult("name" + i, "name" + random.nextInt(9000));
			cacheData.putQueyrResult("name" + i, random.nextInt(1000) > 500);
			cache.cacheRecord(cacheData);
		}
		watch.split();
		System.out.println("use time:" + watch.getSplitTime());
	}

	public OutterQueryResultDataCache() {
		SeriAndDeser<LongKey> keySeriAndDeser = new SeriAndDeser<LongKey>() {
			@Override
			public void serialize(LongKey obj, ByteBuffer buff) {
				buff.putInt(obj.keyId);
			}

			@Override
			public LongKey deserialize(ByteBuffer buf) {
				return new LongKey(buf.getInt());
			}
		};
		SeriAndDeser<QueryResultDataItem> valueSeriAndDeser = new SeriAndDeser<QueryResultDataItem>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void serialize(QueryResultDataItem obj, ByteBuffer buff) {
				buff.putLong(obj.getRecordId());

				buff.putInt(obj.getQueryResult().size());
				for (Map.Entry<String, Object> entry : obj.getQueryResult().entrySet()) {
					buff.putInt(entry.getKey().getBytes().length);
					buff.put(entry.getKey().getBytes());
					Object value = entry.getValue();
					if (value instanceof String) {
						buff.put(QueryResultDataItem.STRING);
						buff.putInt(value.toString().getBytes().length);
						buff.put(value.toString().getBytes());
					} else if (value instanceof Integer) {
						buff.put(QueryResultDataItem.INTEGER);
						buff.putInt((Integer) value);
					} else if (value instanceof Float) {
						buff.put(QueryResultDataItem.FLOAT);
						buff.putFloat((Float) value);
					} else if (value instanceof Long) {
						buff.put(QueryResultDataItem.LONG);
						buff.putLong((Long) value);
					} else if (value instanceof Boolean) {
						buff.put(QueryResultDataItem.BOOLEAN);
						boolean isOk = (boolean) value;
						buff.put(isOk ? (byte) 1 : (byte) -1);
					} else if (value instanceof SeriAndDeser) {
						((SeriAndDeser) value).serialize(value, buff);
					}
				}
				if (obj.getGroupId() != null) {
					buff.putLong(obj.getGroupId());
				} else {
					buff.putLong(-1l);
				}
			}

			@Override
			public QueryResultDataItem deserialize(ByteBuffer buf) {
				Long recordId = buf.getLong();

				int queryResultSize = buf.getInt();
				QueryResultDataItem qrdi = new QueryResultDataItem(recordId, queryResultSize);
				for (int i = 0; i < queryResultSize; i++) {
					String key = StringDescAndSer.INSTANCE.deserialize(buf);
					byte type = buf.get();
					Object value = null;
					switch (type) {
					case QueryResultDataItem.STRING:
						value = StringDescAndSer.INSTANCE.deserialize(buf);
						break;
					case QueryResultDataItem.INTEGER:
						value = buf.getInt();
						break;
					case QueryResultDataItem.FLOAT:
						value = buf.getFloat();
						break;
					case QueryResultDataItem.LONG:
						value = buf.getLong();
						break;
					case QueryResultDataItem.BOOLEAN:
						value = buf.get() == 1;
						break;
					default:
						break;
					}
					qrdi.putQueyrResult(key, value);
				}
				Long groupId = buf.getLong();
				if (groupId == -1) {
					groupId = null;
				}
				qrdi.setGroupId(groupId);
				return qrdi;
			}
		};
		this.bplusTreeCache = new BplusTree<>(20, "./tree.data", keySeriAndDeser, valueSeriAndDeser);
	}

	@Override
	public boolean cacheRecord(QueryResultDataItem cacheData) {
		LongKey longKey = new LongKey((int) cacheData.getRecordId());
		this.bplusTreeCache.insertOrUpdate(longKey, cacheData);
		return true;
	}

	@Override
	public List<QueryResultDataItem> getCacheRecord(Integer startOffset, Integer batchSize) {
		if (startOffset == null) {
			startOffset = 0;
		}
		if (batchSize == null) {
			batchSize = Integer.MAX_VALUE;
		}
		if (this.orderByDataCache != null) {
			List<OrderByDataItemWithObj> cacheRecord = this.orderByDataCache.getCacheRecord(startOffset, batchSize);
			List<QueryResultDataItem> queryResult = new ArrayList<>(cacheRecord.size());
			LongKey key = new LongKey(0);
			for (OrderByDataItemWithObj obdi : cacheRecord) {
				key.keyId = (int) obdi.getRecordId();
				queryResult.add(this.bplusTreeCache.get(key));
			}
			return queryResult;
		}
		return this.bplusTreeCache.listData(startOffset, batchSize);
	}

	@Override
	public void setOrderByDataCache(SingleOrderByDataCache orderByDataCache) {
		this.orderByDataCache = orderByDataCache;
	}

	@Override
	public void setGroupHavingFilter(Map<Long, Boolean> havingFilter) {
		log.info("havingFilter:{}", havingFilter.size());
		if (GenericsUtils.isNullOrEmpty(havingFilter)) {
			return;
		}
		List<Long> needRemoveKeys = new ArrayList<>();
		Iterator<QueryResultDataItem> iterator = this.bplusTreeCache.iterator();
		while (iterator.hasNext()) {
			QueryResultDataItem next = iterator.next();
			if (!havingFilter.get(next.getGroupId())) {
				needRemoveKeys.add(next.getRecordId());
			}
		}
		System.out.println("checkout over and needRemoveKeys !!!! \t" + needRemoveKeys.size());
		LongKey lk = new LongKey(0);
		for (Long keyId : needRemoveKeys) {
			lk.keyId = keyId.intValue();
			// System.out.println("remove keyId:" + keyId);
			this.bplusTreeCache.remove(lk);
		}
	}

	public void checkLinkedIsRight() {

	}

	private static class LongKey implements Comparable<LongKey>, DataSizeCountable {

		int keyId;

		/**
		 * @param int1
		 */
		public LongKey(int keyId) {
			this.keyId = keyId;
		}

		@Override
		public int sizeOfData() {
			return 4;
		}

		@Override
		public int compareTo(LongKey o) {
			return keyId - o.keyId;
		}

		@Override
		public String toString() {
			return "LongKey [keyId=" + keyId + "]";
		}

	}

	@Override
	public long allocateRecordId() {
		return this.recordCount.getAndIncrement();
	}

	@Override
	public void setGroupHavingFilter(GroupFilter groupFilter) {

	}

	@Override
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
		System.out.println("set distinct:" + this.distinct);
	}

}
