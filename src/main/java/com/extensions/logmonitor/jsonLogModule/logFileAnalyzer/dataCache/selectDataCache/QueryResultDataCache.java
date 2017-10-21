package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache;

import java.util.List;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache.SingleOrderByDataCache;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group.GroupFilter;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
public interface QueryResultDataCache {

	public long allocateRecordId();

	public void cacheRecord(QueryResultDataItem cacheData);

	public List<QueryResultDataItem> getCacheRecord(Integer startOffset, Integer batchSize);

	/**
	 * @param orderByDataCache
	 */
	void setOrderByDataCache(SingleOrderByDataCache orderByDataCache);

	/**
	 * @param havingFilter
	 */
	public void setGroupHavingFilter(Map<Long, Boolean> havingFilter);

	public void setGroupHavingFilter(GroupFilter groupFilter);

}
