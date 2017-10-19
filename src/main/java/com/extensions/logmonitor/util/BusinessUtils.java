package com.extensions.logmonitor.util;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.OptExecute;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月13日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class BusinessUtils {

	public static boolean isInWhereCondition(OptExecute optExecute) {
		String matchPath = optExecute.getMatchPath();
		return !matchPath.startsWith(OptExecute.FUN_CALL_PREFIX);
	}

}
