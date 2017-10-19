package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class EqOpt extends OptExecuteCommon<Object> {

	/**
	 * @param matchValue
	 */
	public EqOpt(Object matchValue) {
		super(matchValue);
	}

	@Override
	protected String getOptType() {
		return eq;
	}
}
