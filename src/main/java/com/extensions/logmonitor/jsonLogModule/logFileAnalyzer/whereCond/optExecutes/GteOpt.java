package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class GteOpt extends OptExecuteCommon<Object> {

	public GteOpt(Object matchValue) {
		super(matchValue);
	}

	@Override
	protected String getOptType() {
		return gte;
	}

}
