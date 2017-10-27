package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions.valueConvert.ValueConvert;

import lombok.Data;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月11日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
public class GroupByItem {
	private String groupByPath;
	private ValueConvert valueConvert;

	public GroupByItem(String groupByPath) {
		this(groupByPath, null);
	}

	public GroupByItem(String groupByPath, ValueConvert valueConvert) {
		this.groupByPath = groupByPath;
		this.valueConvert = valueConvert;
	}

}
