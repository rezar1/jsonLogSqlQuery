package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
import com.extensions.logmonitor.util.GenericsUtils;

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

	private List<QueryExecute<? extends Object>> allQueryExecute = new ArrayList<>();
	private Map<String, List<QueryExecute<? extends Object>>> queryExecuteCache = new LinkedHashMap<>();
	private Map<String, List<QueryExecute<? extends Object>>> multiFunQueryExecute = new LinkedHashMap<>();

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

	/**
	 * @param antrlParseFieldPaths
	 */
	public void fillParseFieldPaths(Set<String> antrlParseFieldPaths) {
		antrlParseFieldPaths.addAll(this.queryExecuteCache.keySet());
		antrlParseFieldPaths.addAll(this.multiFunQueryExecute.keySet());
	}

	public SelectPart addFunQueryExecute(QueryExecute<? extends Object> queryExecute) {
		querySize++;
		GenericsUtils.addListIfNotExistsAndRet(this.multiFunQueryExecute, queryExecute.getQueryPathWithFieldName(),
				queryExecute);
		allQueryExecute.add(queryExecute);
		return this;
	}

	public SelectPart addQueryExecute(QueryExecute<? extends Object> queryExecute) {
		querySize++;
		log.debug("queryExecute is:{} and superPath:{} and fullPath:{}", queryExecute, queryExecute.getQuerySuperPath(),
				queryExecute.getQueryPathWithFieldName());
		GenericsUtils.addListIfNotExistsAndRet(this.queryExecuteCache, queryExecute.getQueryPathWithFieldName(),
				queryExecute);
		allQueryExecute.add(queryExecute);
		return this;
	}

	public List<QueryExecute<? extends Object>> getAllQueryExecute() {
		return this.allQueryExecute;
	}

	/**
	 * 获取查询处理器
	 * 
	 * @param superPath
	 * @return
	 */
	public List<QueryExecute<? extends Object>> getAllQueryExecuteWithSuperPath(String superPath) {
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

}
