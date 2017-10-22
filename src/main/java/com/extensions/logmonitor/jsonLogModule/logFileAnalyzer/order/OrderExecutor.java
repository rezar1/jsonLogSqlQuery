package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache.OutterFileOrderByDataCache;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache.SingleOrderByDataCache;
import com.extensions.logmonitor.jsonLogModule.queryExecute.Clearable;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class OrderExecutor implements Clearable {

	// 系统配置
	private Map<String, OrderByItem> orderByItemMaps = new LinkedHashMap<>();
	private Map<String, Integer> orderByItemIndexMaps = new LinkedHashMap<>();
	private List<OrderType> orderTypes = new ArrayList<>();
	private int itemIndex = 0;

	private SingleOrderByDataCache orderDataCache;

	public OrderExecutor() {

	}

	public OrderExecutor addOrderByDataItem(OrderByDataItemWithObj orderByDataItemWithObj) {
		synchronized (this) {
			this.getSingleOrderByDataCache().cacheRecord(orderByDataItemWithObj);
		}
		return this;
	}

	public OrderExecutor addOrderByItem(OrderByItem orderByItem) {
		orderByItemMaps.put(orderByItem.getOrderByPath(), orderByItem);
		orderTypes.add(orderByItem.getOrderType());
		orderByItemIndexMaps.put(orderByItem.getOrderByPath(), itemIndex++);
		return this;
	}

	public SingleOrderByDataCache getSingleOrderByDataCache() {
		if (this.orderDataCache == null) {
			this.orderDataCache = new OutterFileOrderByDataCache();
			this.orderDataCache.setOrderTypes(orderTypes);
		}
		return this.orderDataCache;
	}

	public int isOrderByField(String fieldPathName) {
		Integer index = this.orderByItemIndexMaps.get(fieldPathName);
		if (index == null) {
			return -1;
		}
		return index;
	}

	@Override
	public void clearResource() {
	}

	/**
	 * @return
	 */
	public int getOrderBySize() {
		return this.orderByItemMaps.size();
	}

	/**
	 * 
	 */
	public void executeSort() {
		this.getSingleOrderByDataCache().executeSort();
	}

}
