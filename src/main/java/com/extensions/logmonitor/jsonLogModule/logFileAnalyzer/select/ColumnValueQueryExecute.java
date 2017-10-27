package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月7日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class ColumnValueQueryExecute extends BaseQueryExecute<Object> {

	private Object value;

	@Override
	public void execute(Object value, Long groupId) {
		if (value != null) {
			this.value = super.convertValue(value);
		}
	}

	@Override
	public Object end(Long groupId) {
		return this.value;
	}

}
