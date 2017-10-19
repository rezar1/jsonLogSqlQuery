package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order;

import java.util.Comparator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

/****
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月10日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class OrderTypeComparator implements Comparator<OrderByDataItemWithObj> {

	private byte[] orderTypes;

	public OrderTypeComparator() {

	}

	public OrderTypeComparator(List<OrderType> orderTypes) {
		setOrderTypes(orderTypes);
	}

	/**
	 * @param orderTypes
	 */
	public void setOrderTypes(List<OrderType> orderTypes) {
		if (orderTypes != null && !orderTypes.isEmpty()) {
			this.orderTypes = new byte[orderTypes.size()];
			int index = 0;
			for (OrderType orderType : orderTypes) {
				this.orderTypes[index++] = (byte) orderType.orderType;
			}
		}
	}

	@Override
	public int compare(OrderByDataItemWithObj o1, OrderByDataItemWithObj o2) {
		int result = 0;
		int index = 0;
		Object[] o1CacheData = o1.getOrderByValues();
		Object[] o2CacheData = o2.getOrderByValues();
		for (; index < o1CacheData.length; index++) {
			Object currentValue = o1CacheData[index];
			Object compareValue = null;
			byte orderType = orderTypes[index];
			if (index < o2CacheData.length) {
				compareValue = o2CacheData[index];
			}
			if (currentValue == null && compareValue == null) {
				continue;
			}
			if (currentValue == null && compareValue != null) {
				result = 1 * orderType;
				break;
			}
			if (compareValue == null) {
				result = -1 * orderType;
				break;
			}
			if (currentValue instanceof Integer && compareValue instanceof Integer) {
				result = ((Integer) currentValue).compareTo((Integer) compareValue) * orderType;
			} else if (currentValue instanceof Double && compareValue instanceof Double) {
				result = ((Double) currentValue).compareTo((Double) compareValue) * orderType;
			} else if (currentValue instanceof String && compareValue instanceof String) {
				result = ((String) currentValue).compareTo((String) compareValue) * orderType;
			} else if (currentValue instanceof Boolean && compareValue instanceof Boolean) {
				result = ((Boolean) currentValue).compareTo((Boolean) compareValue) * orderType;
			} else {
				log.warn("can not compare with:{} and {}", currentValue, compareValue);
			}
			if (result != 0) {
				break;
			}
		}
		return result;
	}

}
