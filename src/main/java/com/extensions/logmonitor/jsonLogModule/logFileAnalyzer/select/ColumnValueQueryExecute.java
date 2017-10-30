package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

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
	private boolean inArrayQuery;
	private List<Object> allQueryResult;

	private static final Pattern inArrayPatter;
	static {
		inArrayPatter = Pattern.compile(".*?\\[[#\\d*]\\].*");
	}

	@Override
	public void execute(Object value, Long groupId) {
		if (value != null) {
			value = super.convertValue(value);
		}
		if (this.inArrayQuery) {
			this.allQueryResult.add(value);
		} else {
			this.value = value;
		}
	}

	@Override
	public void setQueryPath(String queryPath) {
		super.setQueryPath(queryPath);
		this.inArrayQuery = inArrayPatter.matcher(queryPath).matches();
		if (this.inArrayQuery) {
			this.allQueryResult = new ArrayList<>();
		}
	}

	@Override
	public Object end(Long groupId) {
		if (this.inArrayQuery) {
			return this.allQueryResult;
		} else {
			return this.value;
		}
	}

	public static void main(String[] args) {
		ColumnValueQueryExecute cv = new ColumnValueQueryExecute();
		cv.setQueryPath("app.info[*].age");
		cv.execute("hello", null);
		System.out.println(cv.end(null));
	}

}
