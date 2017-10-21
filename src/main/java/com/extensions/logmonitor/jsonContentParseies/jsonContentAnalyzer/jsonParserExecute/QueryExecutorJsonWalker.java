package com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.ExecuteLazy;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group.GroupExecutor;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderExecutor;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.QueryExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.OptExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;
import com.extensions.logmonitor.util.GenericsUtils;
import com.extensions.logmonitor.util.TwoTuple;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月19日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class QueryExecutorJsonWalker {

	private List<ExecuteLazy> queryCache;
	private QueryResultDataItem queryReusltDataItem;
	private OrderByDataItemWithObj orderByDataItemWithObj;
	public QueryExecutor queryExecutor = null;
	private boolean isGroup;
	private Map<OptExecute, Boolean> executeWhereResult = new HashMap<>();

	/**
	 * @param qe
	 */
	public QueryExecutorJsonWalker(QueryExecutor qe) {
		this.queryExecutor = qe;
	}

	public void config() {
		this.queryReusltDataItem = this.queryExecutor.createQueryResultDataItem();
		if (this.queryCache == null) {
			this.queryCache = new ArrayList<>();
		} else {
			this.queryCache.clear();
		}
		if (this.queryExecutor.getOrderExecutor() != null) {
			this.orderByDataItemWithObj = new OrderByDataItemWithObj();
			orderByDataItemWithObj.initValueSize(this.queryExecutor.getOrderExecutor().getOrderBySize());
		} else if (this.queryExecutor.getGroupExecutor() != null) {
			isGroup = true;
		}
	}

	/**
	 * 
	 */
	public void doQueryInvoke() {
		try {
			// 如果当前日志行记录检验OK
			if (this.queryExecutor.getWhereCondition().checkWhereIsSuccess(this.executeWhereResult)) {
				// 处理普通取值
				for (ExecuteLazy executeLazy : this.queryCache) {
					if (!executeLazy.isGroup()) {
						executeLazy.duQuery(this.queryReusltDataItem);
					}
				}
				long allocateRecordId = this.queryExecutor.getDataCache().allocateRecordId();
				this.queryReusltDataItem.setRecordId(allocateRecordId);
				boolean isExistsInGroup = false;
				// 如果是group操作,获取分组,并判断是否需要将当前取值添加到集合中去
				if (this.isGroup) {
					TwoTuple<Boolean, Long> putQueryResultDataItem = this.queryExecutor.getGroupExecutor()
							.putQueryResultDataItem(queryReusltDataItem);
					isExistsInGroup = putQueryResultDataItem.first;
					this.queryReusltDataItem.setGroupId(putQueryResultDataItem.second);
				}
				// 处理分组聚合函数
				for (ExecuteLazy executeLazy : this.queryCache) {
					if (executeLazy.isGroup()) {
						executeLazy.duQuery(this.queryReusltDataItem);
					}
				}
				if (this.isGroup) {
					GroupExecutor groupExecutor = this.queryExecutor.getGroupExecutor();
					groupExecutor.doHaving(this.queryReusltDataItem);
				}
				// 根据分组情况判断当前查询结果是否添加到最终结果集合中
				if (!isExistsInGroup) {
					this.queryExecutor.getDataCache().cacheRecord(this.queryReusltDataItem);
				}
				// 处理排序
				if (!isExistsInGroup && this.queryExecutor.getOrderExecutor() != null
						&& this.orderByDataItemWithObj != null) {
					this.orderByDataItemWithObj.setRecordId(this.queryReusltDataItem.getRecordId());
					this.queryExecutor.getOrderExecutor().addOrderByDataItem(orderByDataItemWithObj);
				}
			}
			this.queryCache.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void invokeJsonDataCondition(String superPath, Object value) {
		WhereCondition whereCondition = this.queryExecutor.getWhereCondition();
		Map<String, List<OptExecute>> optExecuteQuickVisitCache = whereCondition.getOptExecuteQuickVisitCache();
		if (optExecuteQuickVisitCache.containsKey(OptExecute.COLUMN_NAME_PREFIX + superPath)) {
			List<OptExecute> optExecutes = optExecuteQuickVisitCache.get(OptExecute.COLUMN_NAME_PREFIX + superPath);
			for (OptExecute optExecute : optExecutes) {
				boolean optSuccess = optExecute.OptSuccess(value);
				log.debug("optExecuteType:{} optExecute :{} and value:{} and optSuccess:{}",
						optExecute.getClass().getSimpleName(), optExecute, value, optSuccess);
				executeWhereResult.put(optExecute, optSuccess);
			}
		}
		this.invokeGroupByCheck(superPath, value);
	}

	public void invokeGroupByCheck(String superPath, Object value) {
		GroupExecutor groupExecutor = this.queryExecutor.getGroupExecutor();
		if (groupExecutor == null) {
			return;
		}
		WhereCondition whereCondition = groupExecutor.getGroupByCondition();
		Map<String, List<OptExecute>> optExecuteQuickVisitCache = whereCondition.getOptExecuteQuickVisitCache();
		if (optExecuteQuickVisitCache.containsKey(OptExecute.COLUMN_NAME_PREFIX + superPath)) {
			List<OptExecute> optExecutes = optExecuteQuickVisitCache.get(OptExecute.COLUMN_NAME_PREFIX + superPath);
			for (OptExecute optExecute : optExecutes) {
				boolean optSuccess = optExecute.OptSuccess(value);
				log.debug("group by optExecuteType:{} optExecute :{} and value:{} and optSuccess:{}",
						optExecute.getClass().getSimpleName(), optExecute, value, optSuccess);
				whereCondition.addWhereExecuteResult(optExecute, optSuccess);
			}
		}
	}

	public void invokeOrderBy(String fieldName, Object value) {
		OrderExecutor orderExecutor = this.queryExecutor.getOrderExecutor();
		if (orderExecutor == null) {
			return;
		}
		int orderByFieldIndex = orderExecutor.isOrderByField(fieldName);
		log.debug("orderByIndex:{} for fieldName:{} and value:{}", orderByFieldIndex, fieldName, value);
		if (orderByFieldIndex != -1) {
			this.orderByDataItemWithObj.addCacheData(orderByFieldIndex, value);
		}
	}

	public void invokeJsonDataQuery(String superPath, Object value) {
		this.invokeJsonDataQuery(superPath, value, false);
	}

	public void invokeJsonDataQuery(String superPath, Object value, boolean notNeedFunCall) {
		QueryExecute<? extends Object> queryExecuteWithSuperPath = this.queryExecutor.getSelectPart()
				.getQueryExecuteWithSuperPath(superPath);
		if (queryExecuteWithSuperPath != null) {
			log.debug("superPath:{} and queryExecute:{} value:{} ", superPath, queryExecuteWithSuperPath, value);
			queryCache.add(ExecuteLazy.newInstance(queryExecuteWithSuperPath, value));
		}
		if (!notNeedFunCall) {
			List<QueryExecute<? extends Object>> allQueryExecutes = this.queryExecutor.getSelectPart()
					.getFunExecuteWithSuperPath(superPath);
			log.debug("superPath:{} and allQueryExecutes:{} value:{}", superPath, allQueryExecutes, value);
			if (GenericsUtils.notNullAndEmpty(allQueryExecutes)) {
				for (QueryExecute<? extends Object> queryExecute : allQueryExecutes) {
					queryCache.add(ExecuteLazy.newInstance(queryExecute, value, true));
				}
			}
			this.invokeGroupByHavingDataQuery(superPath, value);
		}
	}

	private void invokeGroupByHavingDataQuery(String superPath, Object value) {
		GroupExecutor groupExecutor = this.queryExecutor.getGroupExecutor();
		if (groupExecutor == null) {
			return;
		}
		List<QueryExecute<? extends Object>> allQueryExecutes = groupExecutor.getFunExecuteWithSuperPath(superPath);
		log.debug("having superPath:{} and allQueryExecutes:{} value:{}", superPath, allQueryExecutes, value);
		if (GenericsUtils.isNullOrEmpty(allQueryExecutes)) {
			return;
		}
		for (QueryExecute<? extends Object> queryExecute : allQueryExecutes) {
			queryCache.add(ExecuteLazy.newInstance(queryExecute, value, true));
		}
	}

}
