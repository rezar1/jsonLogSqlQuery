package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.valueConvert.ValueConvert;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
public interface OptExecute {

	public static final String FUN_CALL_PREFIX = "FUN_CALL_PREFIX_";
	public static final String COLUMN_NAME_PREFIX = "COLUMN_NAME_PREFIX_";

	public static final String gt = ">";
	public static final String lt = "<";
	public static final String gte = ">=";
	public static final String lte = "<=";
	public static final String eq = "=";
	public static final String notEq = "!=";
	public static final String isNull = "isNull";
	public static final String isNotNull = "isNotNull";
	public static final String like = "like";
	public static final String notLike = "notLike";
	public static final String in = "in";
	public static final String notIn = "notIn";
	public static final String between = "between";
	public static final String notBetween = "notBetween";
	public static final String is = "is";
	public static final String isNot = "isNot";

	public boolean OptSuccess(Object value);

	public void setMatchPath(String path);

	public String getMatchPath();

	public void setValueConvert(ValueConvert valueConvert);

}
