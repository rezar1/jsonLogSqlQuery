package com.extensions.logmonitor.config;

import com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser.JsonLogQuerySqlParser;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;

import lombok.Data;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
public class SearchInfo {

	private String logType;
	private String selectQuery;
	private boolean caseSensitive;
	private QueryExecutor queryExecutor;

	public SearchInfo(String selectQuerySql) {
		this.selectQuery = selectQuerySql;
		this.queryExecutor = JsonLogQuerySqlParser.createQueryExecutor(selectQuery);
		this.logType = this.queryExecutor.getFromTableLogName();
	}

}
