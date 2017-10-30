package com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.ExecuteLazy;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions.valueConvert.ValueConvert;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group.GroupExecutor;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderExecutor;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.QueryExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.SelectPart;
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

	private Map<QueryExecute<? extends Object>, Integer> queryDataIndex;
	private ExecuteLazy[] queryExecuteLazy;
	private List<ExecuteLazy> groupQueryCache;
	private Map<OptExecute, Boolean> executeWhereResult = new HashMap<>();
	private QueryResultDataItem queryReusltDataItem;
	private OrderByDataItemWithObj orderByDataItemWithObj;
	public QueryExecutor queryExecutor = null;
	private boolean isGroup;

	/**
	 * @param qe
	 */
	public QueryExecutorJsonWalker(QueryExecutor qe) {
		this.queryExecutor = qe;
		SelectPart selectPart = this.queryExecutor.getSelectPart();
		List<QueryExecute<? extends Object>> allQueryExecute = selectPart.getAllQueryExecute();
		this.queryDataIndex = new HashMap<>();
		Iterator<QueryExecute<? extends Object>> iterator = allQueryExecute.iterator();
		int index = 0;
		for (; iterator.hasNext();) {
			QueryExecute<? extends Object> next = iterator.next();
			this.queryDataIndex.put(next, index++);
		}
	}

	public void config() {
		this.queryReusltDataItem = this.queryExecutor.createQueryResultDataItem();
		this.executeWhereResult.clear();
		if (this.queryExecuteLazy == null) {
			queryExecuteLazy = new ExecuteLazy[queryDataIndex.size()];
			this.groupQueryCache = new ArrayList<>();
		} else {
			Arrays.fill(this.queryExecuteLazy, null);
			this.groupQueryCache.clear();
		}
		if (this.queryExecutor.getGroupExecutor() != null) {
			isGroup = true;
		}
		if (this.queryExecutor.getOrderExecutor() != null) {
			if (this.isGroup) {
				checkoutOrderByIsLegal();
			}
			this.orderByDataItemWithObj = new OrderByDataItemWithObj();
			orderByDataItemWithObj.initValueSize(this.queryExecutor.getOrderExecutor().getOrderBySize());
		}
	}

	/**
	 * 
	 */
	private void checkoutOrderByIsLegal() {
		OrderExecutor orderExecutor = this.queryExecutor.getOrderExecutor();
		Set<String> paths = new HashSet<>();
		orderExecutor.fillParseFieldPaths(paths);
		GroupExecutor groupExecutor = this.queryExecutor.getGroupExecutor();
		Set<String> pathOfGroup = new HashSet<>();
		groupExecutor.fillParseFieldPaths(pathOfGroup);
		for (String path : paths) {
			if (!pathOfGroup.contains(path)) {
				throw new IllegalArgumentException(
						String.format("bad sql with wrong order by field:%s which not in group items", path));
			}
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
				for (ExecuteLazy executeLazy : this.queryExecuteLazy) {
					if (executeLazy != null && !executeLazy.isGroup()) {
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
				for (ExecuteLazy executeLazy : this.queryExecuteLazy) {
					if (executeLazy != null && executeLazy.isGroup()) {
						executeLazy.duQuery(this.queryReusltDataItem);
					}
				}
				// 处理分组聚合函数
				// for (ExecuteLazy executeLazy : this.groupQueryCache) {
				// if (executeLazy.isGroup()) {
				// executeLazy.duQuery(this.queryReusltDataItem);
				// }
				// }
				if (this.isGroup) {
					GroupExecutor groupExecutor = this.queryExecutor.getGroupExecutor();
					groupExecutor.doWhereConditionQuery(this.queryReusltDataItem, this.groupQueryCache);
					groupExecutor.doHaving(this.queryReusltDataItem);
				}
				// 根据分组情况判断当前查询结果是否添加到最终结果集合中
				if (!isExistsInGroup) {
					boolean cacheRecord = this.queryExecutor.cacheRecord(this.queryReusltDataItem);
					// 处理排序
					if (cacheRecord && this.queryExecutor.getOrderExecutor() != null
							&& this.orderByDataItemWithObj != null) {
						this.orderByDataItemWithObj.setRecordId(this.queryReusltDataItem.getRecordId());
						OrderExecutor orderExecutor = this.queryExecutor.getOrderExecutor();
						Map<String, Object> queryResult = this.queryReusltDataItem.getQueryResult();
						for (String path : orderExecutor.orderByPath()) {
							if (queryResult.containsKey(path)) {
								int orderByField = orderExecutor.isOrderByField(path);
								ValueConvert valueConvert = orderExecutor.getValueConvert(orderByField);
								Object objectValue = queryResult.get(path);
								objectValue = (valueConvert == null) ? objectValue : valueConvert.convert(objectValue);
								this.orderByDataItemWithObj.addCacheData(orderByField, objectValue);
							}
						}
						this.queryExecutor.getOrderExecutor().addOrderByDataItem(orderByDataItemWithObj);
					}
				}
			}
			this.groupQueryCache.clear();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void invokeJsonDataCondition(String superPath, Object value) {
		WhereCondition whereCondition = this.queryExecutor.getWhereCondition();
		List<OptExecute> optExecutes = whereCondition.findOptExecutes(superPath);
		if (GenericsUtils.notNullAndEmpty(optExecutes)) {
			for (OptExecute optExecute : optExecutes) {
				boolean optSuccess = optExecute.OptSuccess(value);
				log.debug("optExecuteType:{} optExecute :{} and value:{} and optSuccess:{}",
						optExecute.getClass().getSimpleName(), optExecute, value, optSuccess);
				Boolean preOptExecute = this.executeWhereResult.get(optExecute);
				if (preOptExecute != null) {
					optSuccess = optExecute.isArrayAllCheck() ? preOptExecute && optSuccess
							: preOptExecute || optSuccess;
				}
				executeWhereResult.put(optExecute, optSuccess);
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
			ValueConvert valueConvert = orderExecutor.getValueConvert(orderByFieldIndex);
			value = (valueConvert == null) ? value : valueConvert.convert(value);
			this.orderByDataItemWithObj.addCacheData(orderByFieldIndex, value);
		}
	}

	public void invokeJsonDataQuery(String superPath, Object value) {
		this.invokeJsonDataQuery(superPath, value, false);
	}

	public void invokeJsonDataQuery(String superPath, Object value, boolean notNeedFunCall) {
		List<QueryExecute<? extends Object>> queryExecuteWithSuperPath = this.queryExecutor.getSelectPart()
				.getAllQueryExecuteWithSuperPath(superPath);
		if (queryExecuteWithSuperPath != null) {
			if (GenericsUtils.notNullAndEmpty(queryExecuteWithSuperPath)) {
				for (QueryExecute<? extends Object> queryExecute : queryExecuteWithSuperPath) {
					log.debug("superPath:{} and queryExecute:{} value:{} ", superPath, queryExecute, value);
					this.queryExecuteLazy[this.queryDataIndex.get(queryExecute)] = ExecuteLazy.newInstance(queryExecute,
							value);
					// queryCache.add(this.queryDataIndex.get(superPath),
					// ExecuteLazy.newInstance(queryExecute, value));
				}
			}
		}
		if (!notNeedFunCall) {
			List<QueryExecute<? extends Object>> allQueryExecutes = this.queryExecutor.getSelectPart()
					.getFunExecuteWithSuperPath(superPath);
			log.debug("superPath:{} and allQueryExecutes:{} value:{}", superPath, allQueryExecutes, value);
			if (GenericsUtils.notNullAndEmpty(allQueryExecutes)) {
				for (QueryExecute<? extends Object> queryExecute : allQueryExecutes) {
					this.queryExecuteLazy[this.queryDataIndex.get(queryExecute)] = ExecuteLazy.newInstance(queryExecute,
							value, true);
					// queryCache.add(this.queryDataIndex.get(superPath),
					// ExecuteLazy.newInstance(queryExecute, value, true));
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
		if (GenericsUtils.isNullOrEmpty(allQueryExecutes)) {
			return;
		}
		log.debug("having superPath:{} and allQueryExecutes:{} value:{}", superPath, allQueryExecutes, value);
		for (QueryExecute<? extends Object> queryExecute : allQueryExecutes) {
			groupQueryCache.add(ExecuteLazy.newInstance(queryExecute, value, true));
		}
	}

}
