package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
import com.extensions.logmonitor.util.GenericsUtils;
import com.extensions.logmonitor.util.MultiKeyMap;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月7日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
@Slf4j
public class SelectPart {

	private boolean distinct;
	private int querySize;

	private MultiKeyMap<String, QueryExecute<? extends Object>> queryExecuteCache = new MultiKeyMap<String, QueryExecute<? extends Object>>();
	private Map<String, List<QueryExecute<? extends Object>>> multiFunQueryExecute = new HashMap<>();

	public void fillAllFunQuery(List<QueryResultDataItem> cacheRecord) {
		for (QueryResultDataItem qrdi : cacheRecord) {
			Long groupId = qrdi.getGroupId();
			for (List<QueryExecute<? extends Object>> allFunQuery : multiFunQueryExecute.values()) {
				for (QueryExecute<? extends Object> funQuery : allFunQuery) {
					Object endValue = null;
					endValue = funQuery.end(groupId);
					qrdi.putQueyrResult(funQuery.getShowName(), endValue);
				}
			}
		}
		for (List<QueryExecute<? extends Object>> allFunQuery : multiFunQueryExecute.values()) {
			for (QueryExecute<? extends Object> funQuery : allFunQuery) {
				funQuery.clearResource();
			}
		}
	}

	public SelectPart addFunQueryExecute(QueryExecute<? extends Object> queryExecute) {
		querySize++;
		GenericsUtils.addListIfNotExists(this.multiFunQueryExecute, queryExecute.getQueryPathWithFieldName(),
				queryExecute);
		return this;
	}

	public SelectPart addQueryExecute(QueryExecute<? extends Object> queryExecute) {
		querySize++;
		log.debug("queryExecute is:{} and superPath:{} and fullPath:{}", queryExecute, queryExecute.getQuerySuperPath(),
				queryExecute.getQueryPathWithFieldName());
		this.queryExecuteCache.put(queryExecute, queryExecute.getQueryPathWithFieldName());
		return this;
	}

	/**
	 * 获取查询处理器
	 * 
	 * @param superPath
	 * @return
	 */
	public QueryExecute<? extends Object> getQueryExecuteWithSuperPath(String superPath) {
		return this.queryExecuteCache.get(superPath);
	}

	/**
	 * 获取方法执行处理器
	 * 
	 * @param superPath
	 * @return
	 */
	public List<QueryExecute<? extends Object>> getFunExecuteWithSuperPath(String superPath) {
		return this.multiFunQueryExecute.get(superPath);
	}

	public static void main(String[] args) {
		ColumnValueQueryExecute columnValueQueryExecute = new ColumnValueQueryExecute();
		String queryPath = "appInfo.geo.stree";
		columnValueQueryExecute.setQueryPath(queryPath);
		SelectPart sp = new SelectPart();
		sp.addQueryExecute(columnValueQueryExecute);
		QueryExecute<? extends Object> queryExecute = sp.getQueryExecuteWithSuperPath("appInfo.geo.stree");
		System.out.println(queryExecute);
	}

}
