package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderType;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderTypeComparator;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc 基于内存对排序数据进行存储
 *
 */
public class InnterOrderByDataCache implements SingleOrderByDataCache {

	private List<OrderByDataItemWithObj> cacheDatas = new ArrayList<>();

	private Comparator<OrderByDataItemWithObj> orderTypeComparator;

	@Override
	public void executeSort() {
		Collections.sort(this.cacheDatas, orderTypeComparator);
	}

	@Override
	public void cacheRecord(OrderByDataItemWithObj cacheData) {
		cacheDatas.add(cacheData);
	}

	@Override
	public List<OrderByDataItemWithObj> getCacheRecord(int offset, int batchSize) {
		int endIndex = offset + batchSize;
		if (endIndex >= cacheDatas.size()) {
			endIndex = cacheDatas.size();
		}
		return cacheDatas.subList(offset, endIndex);
	}

	@Override
	public void setOrderTypes(List<OrderType> orderTypes) {
		this.orderTypeComparator = new OrderTypeComparator(orderTypes);
	}

}
