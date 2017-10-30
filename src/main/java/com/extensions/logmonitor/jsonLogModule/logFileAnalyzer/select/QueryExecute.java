package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions.valueConvert.ValueConvert;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public interface QueryExecute<T> {

	public void execute(Object value, Long groupId);

	public T end(Long groupId);

	public void setAlias(String alias);

	public String getShowName();

	public void setQueryPath(String queryPath);

	public String getQuerySuperPath();

	public String getFieldName();

	public String getQueryPathWithFieldName();

	public void setValueConvert(ValueConvert valueConvert);

	public void clearResource();

	public void clearResource(Long groupId);

}
