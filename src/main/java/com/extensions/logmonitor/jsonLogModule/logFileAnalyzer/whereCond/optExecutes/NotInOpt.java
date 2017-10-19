package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes;

import java.util.List;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class NotInOpt extends OptExecuteCommon<List<Object>> {

	public NotInOpt(List<Object> matchValue) {
		super(matchValue);
	}

	@Override
	protected String getOptType() {
		return notIn;
	}

}
