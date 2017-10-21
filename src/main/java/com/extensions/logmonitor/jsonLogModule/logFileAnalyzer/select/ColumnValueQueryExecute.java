package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月7日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class ColumnValueQueryExecute extends BaseQueryExecute<String> {

	private String value;

	@Override
	public void execute(Object value, Long groupId) {
		this.value = value.toString();
	}

	@Override
	public String end(Long groupId) {
		return this.value;
	}

}
