package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache.OutterFileOrderByDataCache;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache.SingleOrderByDataCache;
import com.extensions.logmonitor.jsonLogModule.queryExecute.Clearable;

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
public class OrderExecutor implements Clearable {

	// 系统配置
	private volatile boolean triggerOutterSort;
	private Map<String, OrderByItem> orderByItemMaps = new LinkedHashMap<>();
	private Map<String, Integer> orderByItemIndexMaps = new LinkedHashMap<>();
	private List<OrderType> orderTypes = new ArrayList<>();
	private int itemIndex = 0;

	// 实际分析日志时的缓存
	private ThreadLocal<SingleOrderByDataCache> orderByDataCache = new ThreadLocal<SingleOrderByDataCache>() {
		public SingleOrderByDataCache initialValue() {
			OutterFileOrderByDataCache outterFileOrderByDataCache = new OutterFileOrderByDataCache();
			outterFileOrderByDataCache.setOrderTypes(orderTypes);
			return outterFileOrderByDataCache;
		}
	};

	public OrderExecutor addOrderByDataItem(OrderByDataItemWithObj orderByDataItemWithObj) {
		orderByDataCache.get().cacheRecord(orderByDataItemWithObj);
		return this;
	}

	public OrderExecutor addOrderByItem(OrderByItem orderByItem) {
		orderByItemMaps.put(orderByItem.getOrderByPath(), orderByItem);
		orderTypes.add(orderByItem.getOrderType());
		orderByItemIndexMaps.put(orderByItem.getOrderByPath(), itemIndex++);
		return this;
	}

	public void triggerOutterSort() {
		this.triggerOutterSort = true;
		log.info("triggerOutterSort -- :{}", triggerOutterSort);
		// TODO change inner cache to outter cache
	}

	public SingleOrderByDataCache getSingleOrderByDataCache() {
		return this.orderByDataCache.get();
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
		this.orderByDataCache.remove();
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
		SingleOrderByDataCache singleOrderByDataCache = this.orderByDataCache.get();
		// singleOrderByDataCache.setOrderTypes(orderTypes);
		singleOrderByDataCache.executeSort();
	}

}
