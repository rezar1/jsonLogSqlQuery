//package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import com.extensions.logmonitor.util.StrUtils;
//
///**
// * 
// * @say little Boy, don't be sad.
// * @name Rezar
// * @time 2017年9月19日
// * @Desc this guy is to lazy , noting left.
// *
// */
//public class OrderByDataItemWithUnicodeKey1 implements OrderByDataItem<OrderByDataItemWithUnicodeKey1> {
//
//	private int recordId;
//	private String[] hexValueAsKeys;
//
//	@Override
//	public void initValueSize(int expectSize) {
//		this.hexValueAsKeys = new String[expectSize];
//	}
//
//	@Override
//	public void addCacheData(int index, Object orderByCompareValue) {
//		String hexValue = null;
//		if (orderByCompareValue instanceof Integer) {
//			hexValue = Integer.toHexString((int) orderByCompareValue);
//		} else if (orderByCompareValue instanceof Double) {
//			hexValue = Double.toHexString((double) orderByCompareValue);
//			hexValue = hexValue.replace("0x", "");
//		} else if (orderByCompareValue instanceof String) {
//			hexValue = StrUtils.makeChecksum(StrUtils.convert((String) orderByCompareValue));
//		} else if (orderByCompareValue == null) {
//			hexValue = "FFFF";
//		} else {
//			System.out.println("not support!!!");
//			return;
//		}
//		this.hexValueAsKeys[index] = hexValue;
//	}
//
//	@Override
//	public void setRecordId(long recordId) {
//		this.recordId = (int) recordId;
//	}
//
//	@Override
//	public long getRecordId() {
//		return this.recordId;
//	}
//
//	public String[] getHexValueAsKeys() {
//		return hexValueAsKeys;
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + Arrays.hashCode(hexValueAsKeys);
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		OrderByDataItemWithUnicodeKey1 other = (OrderByDataItemWithUnicodeKey1) obj;
//		if (!Arrays.equals(hexValueAsKeys, other.hexValueAsKeys))
//			return false;
//		return true;
//	}
//
//	public static void main(String[] args) {
//
//		System.out.println(Integer.toHexString(Integer.MAX_VALUE));
//		System.out.println(Double.toHexString(95));
//		System.out.println(Double.toHexString(95.1));
//		System.out.println(Float.toHexString(95));
//		System.out.println(StrUtils.makeChecksum(StrUtils.convert("12")));
//		System.out.println(StrUtils.makeChecksum(StrUtils.convert("012.2")));
//
//		// byte[] orderType = new byte[] { 1, -1, 1 };
//		List<OrderType> orderTypes = Arrays.asList(OrderType.ASC, OrderType.DESC, OrderType.ASC);
//
//		OrderByDataItemWithUnicodeKey1 key1 = new OrderByDataItemWithUnicodeKey1();
//		key1.initValueSize(3);
//		key1.setRecordId(0l);
//		key1.addCacheData(0, 123);
//		key1.addCacheData(1, 789);
//		key1.addCacheData(2, "24");
//
//		OrderByDataItemWithUnicodeKey1 key2 = new OrderByDataItemWithUnicodeKey1();
//		key2.initValueSize(3);
//		key2.setRecordId(1l);
//		key2.addCacheData(0, 123);
//		key2.addCacheData(1, 789);
//		key2.addCacheData(2, "245");
//
//		System.out.println(key1.equals(key2));
//
//		OrderByDataItemWithUnicodeKey1 key3 = new OrderByDataItemWithUnicodeKey1();
//		key3.initValueSize(3);
//		key3.setRecordId(2l);
//		key3.addCacheData(0, 23);
//		key3.addCacheData(1, 89);
//		key3.addCacheData(2, "84");
//
//		List<OrderByDataItemWithUnicodeKey1> keys = new ArrayList<>();
//		keys.add(key3);
//		keys.add(key2);
//		keys.add(key1);
//		Collections.sort(keys, new OrderTypeComparatorWithUnicode(orderTypes));
//		for (Object value : keys) {
//			System.out.println(value);
//		}
//	}
//
//	@Override
//	public String toString() {
//		return "OrderByDataItemWithUnicodeKey [recordId=" + recordId + "]";
//	}
//
//	@Override
//	public Object[] getOrderByValues() {
//		return null;
//	}
//
//}
