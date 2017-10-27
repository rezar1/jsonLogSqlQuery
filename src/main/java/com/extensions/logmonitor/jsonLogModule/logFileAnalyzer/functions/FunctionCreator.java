package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions.valueConvert.StringConvert;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions.valueConvert.StringRegexConvert;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions.valueConvert.TimeConvert;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions.valueConvert.ValueConvert;
import com.extensions.logmonitor.util.TupleUtil;
import com.extensions.logmonitor.util.TwoTuple;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月26日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class FunctionCreator {

	public static final int FUN_TIME_TYPE = 0; // time
	public static final int FUN_CHAR_TYPE = 0; // time

	public static TwoTuple<ValueConvert, List<String>> createValueConvert(int type, String functionName,
			List<Object> params) {
		List<String> paths = new ArrayList<>();
		ValueConvert valueConvert = null;
		if (FUN_TIME_TYPE == type) {
			valueConvert = new TimeConvert();
			if (params.size() == 2) {
				((TimeConvert) valueConvert).setTimeFormat(params.get(0).toString());
				paths.add(params.get(1).toString());
			} else if (params.size() == 1) {
				paths.add(params.get(0).toString());
			}
			if (functionName.equals("DAY")) {
				((TimeConvert) valueConvert).setTakeField(Calendar.DAY_OF_YEAR);
			} else if (functionName.equals("HOUR")) {
				((TimeConvert) valueConvert).setTakeField(Calendar.HOUR_OF_DAY);
			} else if (functionName.equals("MINUTE")) {
				((TimeConvert) valueConvert).setTakeField(Calendar.MINUTE);
			} else if (functionName.equals("YEAR")) {
				((TimeConvert) valueConvert).setTakeField(Calendar.YEAR);
			} else if (functionName.equals("SECOND")) {
				((TimeConvert) valueConvert).setTakeField(Calendar.SECOND);
			}
		} else if (FUN_CHAR_TYPE == type) {
			if (functionName.equals("REGEX_GROUP")) {
				valueConvert = new StringRegexConvert();
				if (params.size() < 3) {
					throw new IllegalArgumentException("format is:regex,groupId,strValue for function:" + functionName);
				}
				((StringRegexConvert) valueConvert).setRegexStr(params.get(0).toString());
				((StringRegexConvert) valueConvert).setGroupId((Integer) params.get(1));
				paths.add(params.get(2).toString());
			} else {
				valueConvert = new StringConvert();
			}
		}
		return TupleUtil.tuple(valueConvert, paths);
	}

}
