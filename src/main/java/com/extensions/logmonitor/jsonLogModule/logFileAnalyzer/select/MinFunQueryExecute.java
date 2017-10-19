package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class MinFunQueryExecute extends BaseQueryExecute<Object> {

	private static final String PRE_VALUE_MIN = "PRE_VALUE_MIN";

	@Override
	public void execute(Object value, Long groupId) {
		if (StringUtils.isNumeric(value.toString())) {
			BigDecimal currentValue = new BigDecimal(value.toString());
			BigDecimal preValue = takeResource(PRE_VALUE_MIN, null, groupId);
			if (preValue == null || preValue.compareTo(currentValue) < 0) {
				putResource(PRE_VALUE_MIN, currentValue, groupId);
			}
		} else if (value instanceof String) {
			String currentValue = value.toString();
			String preStrValue = takeResource(PRE_VALUE_MIN, null, groupId);
			if (preStrValue == null || preStrValue.compareTo(currentValue) < 0) {
				putResource(PRE_VALUE_MIN, currentValue, groupId);
			}
		}
	}

	@Override
	public Object end(Long groupId) {
		return super.takeResource(PRE_VALUE_MIN, null, groupId);
	}

}
