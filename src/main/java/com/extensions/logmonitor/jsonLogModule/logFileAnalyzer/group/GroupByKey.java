package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group;

import java.util.ArrayList;
import java.util.List;

import com.extensions.logmonitor.util.StrUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月11日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class GroupByKey {

	private List<Object> values = new ArrayList<>();
	private String hashValue = "";

	public GroupByKey addGroupByFieldValue(Object value) {
		values.add(value);
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
		}
		this.hashValue += (hexValue);
		return this;
	}

	public Long getHashValue() {
		return (long) this.hashValue.hashCode();
	}

	public List<Object> getValues() {
		return this.values;
	}

}
