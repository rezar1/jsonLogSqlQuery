package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月9日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class AvgFunQueryExecute extends BaseQueryExecute<Object> {

	private static final String PRE_VALUE_SUM = "PRE_VALUE_SUM";
	private static final String PRE_VALUE_SIZE = "PRE_VALUE_AVG";

	@Override
	public void execute(Object value, Long groupId) {
		if (StringUtils.isNumeric(value.toString())) {
			BigDecimal currentValue = new BigDecimal(value.toString());
			BigDecimal preValue = takeResource(PRE_VALUE_SUM, new BigDecimal(0.0), groupId);
			putResource(PRE_VALUE_SUM, currentValue.add(preValue), groupId);
			putResource(PRE_VALUE_SIZE, takeResource(PRE_VALUE_SIZE, 0, groupId) + 1, groupId);
		}
	}

	@Override
	public Object end(Long groupId) {
		BigDecimal sumValue = takeResource(PRE_VALUE_SUM, new BigDecimal(0.0), groupId);
		Integer size = takeResource(PRE_VALUE_SUM, 1, groupId);
		return sumValue.divide(new BigDecimal(size), 2);
	}
}
