package com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.QueryExecute;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月10日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class ExecuteLazy {

	private QueryExecute<? extends Object> queryExecute;
	private Object value;
	private boolean isGroup;

	/**
	 * @param queryExecute
	 * @param value
	 */
	private ExecuteLazy(QueryExecute<? extends Object> queryExecute, Object value, boolean isGroup) {
		this.queryExecute = queryExecute;
		this.value = value;
		this.isGroup = isGroup;
	}

	public void duQuery(QueryResultDataItem queryResultDataItem) {
		this.queryExecute.execute(value, queryResultDataItem.getGroupId());
		// 对于不是group函数的调用结果,直接添加到记录结果集中
		if (!isGroup) {
			try {
				Object value = this.queryExecute.end(queryResultDataItem.getGroupId());
				queryResultDataItem.putQueyrResult(this.queryExecute.getShowName(), value);
			} finally {
				this.queryExecute.clearResource();
			}
		}
	}

	public boolean isGroup() {
		return this.isGroup;
	}

	// public String getFieldPath() {
	// return this.queryExecute.getQueryPathWithFieldName();
	// }

	public static ExecuteLazy newInstance(QueryExecute<? extends Object> queryExecute, Object value) {
		return new ExecuteLazy(queryExecute, value, false);
	}

	public static ExecuteLazy newInstance(QueryExecute<? extends Object> queryExecute, Object value, boolean isGroup) {
		return new ExecuteLazy(queryExecute, value, isGroup);
	}

}
