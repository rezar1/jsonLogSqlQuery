package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache;

import java.util.List;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderType;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
public interface SingleOrderByDataCache {

	public void cacheRecord(OrderByDataItemWithObj cacheData);

	public List<OrderByDataItemWithObj> getCacheRecord(int offset, int batchSize);

	/**
	 * 
	 */
	void executeSort();

	public void setOrderTypes(List<OrderType> orderTypes);

}
