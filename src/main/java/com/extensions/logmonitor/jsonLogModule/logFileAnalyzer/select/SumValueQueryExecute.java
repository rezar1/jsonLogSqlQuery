package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月7日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class SumValueQueryExecute extends BaseQueryExecute<Double> {

	private final String SUMVALUEQUERY = "SUMVALUEQUERY";

	@Override
	public void execute(Object value, Long groupId) {
		if (value != null && StringUtils.isNumeric(value.toString())) {
			BigDecimal takeResource = super.takeResource(SUMVALUEQUERY, new BigDecimal("0.0"), groupId);
			super.putResource(SUMVALUEQUERY, takeResource.add(new BigDecimal(value.toString())), groupId);
		}
	}

	@Override
	public Double end(Long groupId) {
		return super.takeResource(SUMVALUEQUERY, new BigDecimal("0.0"), groupId).doubleValue();
	}

}
