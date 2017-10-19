package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache.SingleOrderByDataCache;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.util.GenericsUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class InnterQueryResultDataCache implements QueryResultDataCache {

	private Map<Long, QueryResultDataItem> queryResultItemCache = new LinkedHashMap<>();
	private List<QueryResultDataItem> queryResultDataCache = new ArrayList<>();

	private SingleOrderByDataCache orderByDataCache;

	private AtomicLong recordCount = new AtomicLong(0);

	@Override
	public void cacheRecord(QueryResultDataItem cacheData) {
		// 暂时重复存储映射
		queryResultItemCache.put(cacheData.getRecordId(), cacheData);
		queryResultDataCache.add(cacheData);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<QueryResultDataItem> getCacheRecord(Integer startOffset, Integer batchSize) {
		if (startOffset == null) {
			startOffset = 0;
		}
		if (batchSize == null) {
			batchSize = this.queryResultDataCache.size();
		}
		if (startOffset > this.queryResultDataCache.size() - 1) {
			return Collections.EMPTY_LIST;
		}
		if (this.orderByDataCache != null) {
			List<OrderByDataItemWithObj> cacheRecord = this.orderByDataCache.getCacheRecord(startOffset, batchSize);
			List<QueryResultDataItem> queryResult = new ArrayList<>(cacheRecord.size());
			for (OrderByDataItemWithObj obdi : cacheRecord) {
				queryResult.add(this.queryResultItemCache.get(obdi.getRecordId()));
			}
			return queryResult;
		} else {
			int endIndex = startOffset + batchSize;
			if (endIndex >= this.queryResultDataCache.size()) {
				endIndex = this.queryResultDataCache.size();
			}
			return queryResultDataCache.subList(startOffset, endIndex);
		}
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
		Iterator<QueryResultDataItem> iterator = this.queryResultDataCache.iterator();
		while (iterator.hasNext()) {
			if (!havingFilter.get(iterator.next().getGroupId())) {
				iterator.remove();
			}
		}
	}

	@Override
	public long allocateRecordId() {
		return this.recordCount.getAndIncrement();
	}

}
