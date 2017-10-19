package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes;

import java.util.List;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月6日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class BetweenOpt extends OptExecuteCommon<List<Object>> {

	/**
	 * @param matchValue
	 */
	public BetweenOpt(List<Object> matchValue) {
		super(matchValue);
	}

	@Override
	protected String getOptType() {
		return between;
	}

}
