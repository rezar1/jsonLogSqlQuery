package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.extensions.logmonitor.util.StrUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月19日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class UnicodeKey implements Comparable<UnicodeKey>, DataSizeCountable {

	private int index;
	private String[] hexValueAsKeys;
	private byte[] orderType;

	public UnicodeKey(int size, byte[] orderType) {
		this.hexValueAsKeys = new String[size];
		this.orderType = orderType;
	}

	/**
	 * 
	 */
	private UnicodeKey() {
	}

	@Override
	public int compareTo(UnicodeKey o) {
		int result = 0;
		int index = 0;
		for (; index < this.hexValueAsKeys.length; index++) {
			String currentValue = this.hexValueAsKeys[index];
			String compareValue = null;
			byte orderTypeBy = this.orderType[index];
			if (index < o.getHexValueAsKeys().length) {
				compareValue = o.getHexValueAsKeys()[index];
			}
			if (currentValue == null && compareValue == null) {
				continue;
			}
			if (currentValue == null && compareValue != null) {
				result = 1 * orderTypeBy;
				break;
			}
			if (compareValue == null) {
				result = -1 * orderTypeBy;
				break;
			}
			result = (currentValue).compareTo(compareValue) * orderTypeBy;
			if (result != 0) {
				break;
			}
		}
		return result;
	}

	public UnicodeKey addKeyObjValue(Object value) {
		String hexValue = null;
		if (value instanceof Integer) {
			hexValue = Integer.toHexString((int) value);
		} else if (value instanceof Double) {
			hexValue = Double.toHexString((double) value);
		} else if (value instanceof String) {
			hexValue = StrUtils.makeChecksum(StrUtils.convert((String) value));
		} else if (value == null) {
			hexValue = "FFFF";
		} else {
			System.out.println("not support!!!");
			return this;
		}
		this.hexValueAsKeys[index++] = hexValue;
		return this;
	}

	public String[] getHexValueAsKeys() {
		return hexValueAsKeys;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(hexValueAsKeys);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnicodeKey other = (UnicodeKey) obj;
		if (!Arrays.equals(hexValueAsKeys, other.hexValueAsKeys))
			return false;
		return true;
	}

	public static void main(String[] args) {
		byte[] orderType = new byte[] { 1, -1, 1 };
		UnicodeKey key1 = new UnicodeKey(3, orderType);
		key1.addKeyObjValue(123);
		key1.addKeyObjValue(789);
		key1.addKeyObjValue("24");

		UnicodeKey key2 = new UnicodeKey(3, orderType);
		key2.addKeyObjValue(123);
		key2.addKeyObjValue(789);
		key2.addKeyObjValue("245");

		System.out.println(key1.equals(key2));

		UnicodeKey key3 = new UnicodeKey(3, orderType);
		key3.addKeyObjValue(223);
		key3.addKeyObjValue(89);
		key3.addKeyObjValue("84");

		List<UnicodeKey> keys = new ArrayList<>();
		keys.add(key3);
		keys.add(key2);
		keys.add(key1);

		Collections.sort(keys);
		for (Object value : keys) {
			System.out.println(value);
		}
	}

	public static UnicodeKey getInstance() {
		return new UnicodeKey(0, null);
	}

	public void serialize(ByteBuffer buf) {
		buf.putInt(this.hexValueAsKeys.length);
		for (String str : this.hexValueAsKeys) {
			buf.putInt(str.length());
			buf.put(str.getBytes());
		}
		buf.putInt(this.orderType.length);
		for (byte orderType : this.orderType) {
			buf.put(orderType);
		}
	}

	public static UnicodeKey deserialize(ByteBuffer buf) {
		UnicodeKey unicodeKey = new UnicodeKey();
		int keyLength = buf.getInt();
		String[] hexValue = new String[keyLength];
		for (int i = 0; i < keyLength; i++) {
			byte[] tempKeyBytes = new byte[buf.getInt()];
			buf.get(tempKeyBytes);
			hexValue[i] = new String(tempKeyBytes);
		}
		int orderTypeNums = buf.getInt();
		byte[] orderTypes = new byte[orderTypeNums];
		for (int i = 0; i < orderTypeNums; i++) {
			orderTypes[i] = buf.get();
		}
		unicodeKey.hexValueAsKeys = hexValue;
		unicodeKey.orderType = orderTypes;
		return unicodeKey;
	}

	@Override
	public int sizeOfData() {
		int size = 0;
		size += 4;
		for (String str : this.hexValueAsKeys) {
			size += 4;
			size += str.getBytes().length;
		}
		size += 4;
		size += this.orderType.length;
		return size;
	}

}
