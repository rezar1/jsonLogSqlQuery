//package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order;
//
//import java.util.Comparator;
//import java.util.List;
//
///**
// * 
// * @say little Boy, don't be sad.
// * @name Rezar
// * @time 2017年10月10日
// * @Desc this guy is to lazy , noting left.
// *
// */
//public class OrderTypeComparatorWithUnicode implements Comparator<OrderByDataItemWithUnicodeKey> {
//
//	private byte[] orderTypes;
//
//	public OrderTypeComparatorWithUnicode(List<OrderType> orderTypes) {
//		if (orderTypes != null && !orderTypes.isEmpty()) {
//			this.orderTypes = new byte[orderTypes.size()];
//			int index = 0;
//			for (OrderType orderType : orderTypes) {
//				this.orderTypes[index++] = (byte) orderType.orderType;
//			}
//		}
//	}
//
//	@Override
//	public int compare(OrderByDataItemWithUnicodeKey o1, OrderByDataItemWithUnicodeKey o2) {
//		int result = 0;
//		int index = 0;
//		String[] hexValueAsKeys = o1.getHexValueAsKeys();
//		String[] hexValueAsKeys2 = o2.getHexValueAsKeys();
//		for (; index < hexValueAsKeys.length; index++) {
//			String currentValue = hexValueAsKeys[index];
//			String compareValue = null;
//			// by default
//			byte orderTypeBy = (byte) OrderType.ASC.orderType;
//			if (this.orderTypes != null && index < this.orderTypes.length) {
//				orderTypeBy = this.orderTypes[index];
//			}
//			if (index < hexValueAsKeys2.length) {
//				compareValue = hexValueAsKeys2[index];
//			}
//			if (currentValue == null && compareValue == null) {
//				continue;
//			}
//			if (currentValue == null && compareValue != null) {
//				result = 1 * orderTypeBy;
//				break;
//			}
//			if (compareValue == null) {
//				result = -1 * orderTypeBy;
//				break;
//			}
//			result = (currentValue).compareTo(compareValue) * orderTypeBy;
//			if (result != 0) {
//				break;
//			}
//		}
//		if (result == 0) {
//			// 如果两个数据大小相同,按照recordId进行排序
//			result = (int) (o1.getRecordId() - o2.getRecordId());
//		}
//		return result;
//	}
//
//}
