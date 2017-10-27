package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang.time.StopWatch;

import com.extensions.logmonitor.exceptions.DataCleanedExeception;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.SeriAndDeser;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.utils.StringDescAndSer;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache.SingleOrderByDataCache;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group.GroupFilter;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group.GroupIdContact;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.util.BinaryFileStore;
import com.extensions.logmonitor.util.BloomFilter;
import com.google.common.collect.Lists;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月17日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class FileOutterQueryResultDataCache implements QueryResultDataCache {

	private AtomicInteger totalCount = new AtomicInteger(0);
	private SingleOrderByDataCache orderByDataCache;
	private BinaryFileStore<QueryResultDataItem> fileStore;
	private List<Long> orderByOffset = Lists.newArrayList();
	private BloomFilter distinctFilter;
	private boolean distinct;

	public FileOutterQueryResultDataCache() {
		SeriAndDeser<QueryResultDataItem> valueSeriAndDeser = new SeriAndDeser<QueryResultDataItem>() {
			@SuppressWarnings({ "unchecked", "rawtypes" })
			public void serialize(QueryResultDataItem obj, ByteBuffer buff) {
				buff.putLong(obj.getRecordId());
				buff.putInt(obj.getQueryResult().size());
				LinkedHashMap<String, Object> queryResult = obj.getQueryResult();
				for(Iterator<Entry<String, Object>> it = queryResult.entrySet().iterator();it.hasNext();){  
					Entry<String, Object> entry = (Entry<String, Object>)it.next();  
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
		File tempFile = new File("./temp_" + UUID.randomUUID().toString().replaceAll("_", "") + ".data");
		if (!tempFile.exists()) {
			try {
				tempFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		tempFile.deleteOnExit();
		tempFile.deleteOnExit();
		fileStore = new BinaryFileStore<>(tempFile, valueSeriAndDeser);
		fileStore.open();
	}

	public boolean cacheRecord(QueryResultDataItem cacheData) {
		String dataMarkStr = cacheData.parseValueStrMark();
		if (this.distinct) {
			boolean contains = distinctFilter.contains(dataMarkStr);
			if (contains) {
				// to dup check,存在误报,待优化 TODO
				return false;
			} else {
				this.distinctFilter.add(dataMarkStr);
			}
		}
		totalCount.incrementAndGet();
		long currentPos = this.fileStore.currentPosition();
		cacheData.setOffset(currentPos);
		fileStore.set(cacheData);
		return true;
	}

	public void triggerOver() {
		this.fileStore.sync();
	}

	public void testReadData() {
		this.fileStore.changeToRead();
		for (Long offset : this.orderByOffset) {
			System.out.println(this.fileStore.read(offset));
		}
	}

	public List<QueryResultDataItem> getCacheRecord(Integer startOffset, Integer batchSize) {
		this.triggerOver();
		this.fileStore.changeToRead();
		if (startOffset == null) {
			startOffset = 0;
		}
		if (batchSize == null) {
			batchSize = this.totalCount.get();
		}
		List<QueryResultDataItem> result = new ArrayList<>(batchSize);
		if (this.orderByDataCache != null) {
			List<OrderByDataItemWithObj> cacheRecord = this.orderByDataCache.getCacheRecord(startOffset, batchSize);
			for (OrderByDataItemWithObj obdi : cacheRecord) {
				try {
					QueryResultDataItem read = this.fileStore.read(obdi.getRecordId());
					result.add(read);
				} catch (DataCleanedExeception e) {
					System.out.println("data with recordId:" + obdi.getRecordId() + " has cleaned");
				}
			}
		} else {
			int endOffset = (startOffset + batchSize);
			for (int i = 0; i < this.totalCount.get(); i++) {
				try {
					QueryResultDataItem readData = this.fileStore.read(null);
					if (i >= startOffset && i < endOffset) {
						result.add(readData);
					}
				} catch (DataCleanedExeception e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return result;
	}

	/**
	 * distinct 必须放在select_list开头
	 */
	@Override
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
		System.out.println("set distinct:" + this.distinct);
		if (this.distinct) {
			this.distinctFilter = new BloomFilter();
		} else {
			this.distinctFilter = null;
		}
	}

	public void setOrderByDataCache(SingleOrderByDataCache orderByDataCache) {
		this.orderByDataCache = orderByDataCache;
	}

	public void setGroupHavingFilter(Map<Long, Boolean> havingFilter) {

	}

	public void setGroupHavingFilter(GroupFilter groupFilter) {
		Iterator<GroupIdContact> iterator = groupFilter.iterator();
		while (iterator.hasNext()) {
			GroupIdContact next = iterator.next();
			if (next.isNeedRemove()) {
				this.fileStore.clean(next.getRecordId());
			}
		}
	}

	/**
	 * 不做处理,在保存的时候分配
	 */
	@Override
	public long allocateRecordId() {
		return this.fileStore.currentPosition();
	}

	/**
	 * 377404
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Random random = new Random();
		StopWatch watch = new StopWatch();
		watch.start();
		FileOutterQueryResultDataCache cache = new FileOutterQueryResultDataCache();
		for (int i = 0; i < 10000; i++) {
			QueryResultDataItem cacheData = new QueryResultDataItem(i, 3);
			cacheData.putQueyrResult("name" + i, i);
			cacheData.putQueyrResult("name" + i + "name", "name" + random.nextInt(9000));
			cacheData.putQueyrResult("name" + i + "isChild", random.nextInt(1000) > 500);
			cache.cacheRecord(cacheData);
		}
		watch.split();
		System.out.println("use time:" + watch.getSplitTime());
		watch.split();
		cache.triggerOver();
		System.out.println("use time:" + watch.getSplitTime());
		List<QueryResultDataItem> cacheRecord = cache.getCacheRecord(10, 20);
		for (QueryResultDataItem data : cacheRecord) {
			System.out.println(data);
		}
	}

}
