package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class NotEqOpt extends OptExecuteCommon<Object> {

	/**
	 * @param eqValue
	 */
	public NotEqOpt(Object eqValue) {
		super(eqValue);
	}

	@Override
	protected String getOptType() {
		return notEq;
	}

}
