//package com.extensions.logmonitor.jsonContentParseies.copy;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.antlr.v4.runtime.tree.ParseTreeProperty;
//import org.antlr.v4.runtime.tree.TerminalNode;
//
//import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.ArrayPartContext;
//import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.FalseValueContext;
//import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.JsonFileRootContext;
//import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.KeyValueContext;
//import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.NullValueContext;
//import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.NumberValueContext;
//import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.ObjectPartContext;
//import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.StringValueContext;
//import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.TrueValueContext;
//import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.ExecuteLazy;
//import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope.ArrayScope;
//import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope.JsonSuperScope;
//import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope.ObjPairKeyValueScope;
//import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope.ObjectScope;
//import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
//import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group.GroupExecutor;
//import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByDataItemWithObj;
//import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderExecutor;
//import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.Scope;
//import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.QueryExecute;
//import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.OptExecute;
//import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;
//import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;
//import com.extensions.logmonitor.util.GenericsUtils;
//import com.extensions.logmonitor.util.StrUtils;
//import com.extensions.logmonitor.util.TwoTuple;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class JsonContentAnalyzer2 extends jsonBaseListener {
//
//	ParseTreeProperty<Scope> scope;
//	public QueryExecutor queryExecutor = null;
//	private JsonSuperScope jsonSuperScope;
//	private boolean isGroup;
//
//	private Scope currentScope;
//	private List<ExecuteLazy> queryCache;
//	private QueryResultDataItem queryReusltDataItem;
//	private OrderByDataItemWithObj orderByDataItemWithObj;
//
//	private Map<OptExecute, Boolean> executeWhereResult = new HashMap<>();
//
//	public JsonContentAnalyzer2(QueryExecutor queryExecutor) {
//		this.queryExecutor = queryExecutor;
//		this.config();
//		System.out.println("init over!!!");
//	}
//
//	private void config() {
//		this.scope = new ParseTreeProperty<>();
//		this.jsonSuperScope = new JsonSuperScope();
//		this.queryReusltDataItem = this.queryExecutor.createQueryResultDataItem();
//		this.currentScope = jsonSuperScope;
//		if (this.queryCache == null) {
//			this.queryCache = new ArrayList<>();
//		} else {
//			this.queryCache.clear();
//		}
//		if (this.queryExecutor.getOrderExecutor() != null) {
//			this.orderByDataItemWithObj = new OrderByDataItemWithObj();
//			orderByDataItemWithObj.initValueSize(this.queryExecutor.getOrderExecutor().getOrderBySize());
//		} else if (this.queryExecutor.getGroupExecutor() != null) {
//			isGroup = true;
//		}
//	}
//
//	@Override
//	public void enterJsonFileRoot(JsonFileRootContext ctx) {
//		System.out.println("entry jsonFile");
//	}
//
//	@Override
//	public void exitJsonFileRoot(JsonFileRootContext ctx) {
//		System.out.println("exit jsonFile");
//	}
//
//	@Override
//	public void enterObjectPart(ObjectPartContext ctx) {
//		log.debug("{} and currentPath:{}", "enterObjectPart", this.currentScope.getScopeName());
//		if (this.currentScope instanceof JsonSuperScope) {
//			invokeJsonDataQuery("*", ctx.getText());
//		} else {
//			this.currentScope = new ObjectScope(this.currentScope, "");
//		}
//	}
//
//	private void invokeJsonDataCondition(String superPath, Object value) {
//		WhereCondition whereCondition = this.queryExecutor.getWhereCondition();
//		Map<String, List<OptExecute>> optExecuteQuickVisitCache = whereCondition.getOptExecuteQuickVisitCache();
//		if (optExecuteQuickVisitCache.containsKey(OptExecute.COLUMN_NAME_PREFIX + superPath)) {
//			List<OptExecute> optExecutes = optExecuteQuickVisitCache.get(OptExecute.COLUMN_NAME_PREFIX + superPath);
//			for (OptExecute optExecute : optExecutes) {
//				boolean optSuccess = optExecute.OptSuccess(value);
//				log.debug("optExecuteType:{} optExecute :{} and value:{} and optSuccess:{}",
//						optExecute.getClass().getSimpleName(), optExecute, value, optSuccess);
//				executeWhereResult.put(optExecute, optSuccess);
//			}
//		}
//		this.invokeGroupByCheck(superPath, value);
//	}
//
//	private void invokeGroupByCheck(String superPath, Object value) {
//		GroupExecutor groupExecutor = this.queryExecutor.getGroupExecutor();
//		if (groupExecutor == null) {
//			return;
//		}
//		WhereCondition whereCondition = groupExecutor.getGroupByCondition();
//		Map<String, List<OptExecute>> optExecuteQuickVisitCache = whereCondition.getOptExecuteQuickVisitCache();
//		if (optExecuteQuickVisitCache.containsKey(OptExecute.COLUMN_NAME_PREFIX + superPath)) {
//			List<OptExecute> optExecutes = optExecuteQuickVisitCache.get(OptExecute.COLUMN_NAME_PREFIX + superPath);
//			for (OptExecute optExecute : optExecutes) {
//				boolean optSuccess = optExecute.OptSuccess(value);
//				log.debug("group by optExecuteType:{} optExecute :{} and value:{} and optSuccess:{}",
//						optExecute.getClass().getSimpleName(), optExecute, value, optSuccess);
//				whereCondition.addWhereExecuteResult(optExecute, optSuccess);
//			}
//		}
//	}
//
//	private void invokeOrderBy(String fieldName, Object value) {
//		OrderExecutor orderExecutor = this.queryExecutor.getOrderExecutor();
//		if (orderExecutor == null) {
//			return;
//		}
//		int orderByFieldIndex = orderExecutor.isOrderByField(fieldName);
//		log.debug("orderByIndex:{} for fieldName:{} and value:{}", orderByFieldIndex, fieldName, value);
//		if (orderByFieldIndex != -1) {
//			this.orderByDataItemWithObj.addCacheData(orderByFieldIndex, value);
//		}
//	}
//
//	private void invokeJsonDataQuery(String superPath, Object value) {
//		this.invokeJsonDataQuery(superPath, value, false);
//	}
//
//	private void invokeJsonDataQuery(String superPath, Object value, boolean notNeedFunCall) {
//		QueryExecute<? extends Object> queryExecuteWithSuperPath = this.queryExecutor.getSelectPart()
//				.getQueryExecuteWithSuperPath(superPath);
//		if (queryExecuteWithSuperPath != null) {
//			log.debug("superPath:{} and queryExecute:{} value:{} ", superPath, queryExecuteWithSuperPath, value);
//			queryCache.add(ExecuteLazy.newInstance(queryExecuteWithSuperPath, value));
//		}
//		if (!notNeedFunCall) {
//			List<QueryExecute<? extends Object>> allQueryExecutes = this.queryExecutor.getSelectPart()
//					.getFunExecuteWithSuperPath(superPath);
//			log.debug("superPath:{} and allQueryExecutes:{} value:{}", superPath, allQueryExecutes, value);
//			if (GenericsUtils.notNullAndEmpty(allQueryExecutes)) {
//				for (QueryExecute<? extends Object> queryExecute : allQueryExecutes) {
//					queryCache.add(ExecuteLazy.newInstance(queryExecute, value, true));
//				}
//			}
//			this.invokeGroupByHavingDataQuery(superPath, value);
//		}
//	}
//
//	private void invokeGroupByHavingDataQuery(String superPath, Object value) {
//		GroupExecutor groupExecutor = this.queryExecutor.getGroupExecutor();
//		if (groupExecutor == null) {
//			return;
//		}
//		List<QueryExecute<? extends Object>> allQueryExecutes = groupExecutor.getFunExecuteWithSuperPath(superPath);
//		log.debug("having superPath:{} and allQueryExecutes:{} value:{}", superPath, allQueryExecutes, value);
//		if (GenericsUtils.isNullOrEmpty(allQueryExecutes)) {
//			return;
//		}
//		for (QueryExecute<? extends Object> queryExecute : allQueryExecutes) {
//			queryCache.add(ExecuteLazy.newInstance(queryExecute, value, true));
//		}
//	}
//
//	@Override
//	public void exitObjectPart(ObjectPartContext ctx) {
//		log.debug("{} and currentPath:{}", "exitObjectPart", this.currentScope.getScopeName());
//		doQueryInvoke();
//		this.currentScope = this.currentScope.getEnclosingScope();
//		this.config();
//	}
//
//	/**
//	 * 
//	 */
//	private void doQueryInvoke() {
//		try {
//			// 如果当前日志行记录检验OK
//			if (this.queryExecutor.getWhereCondition().checkWhereIsSuccess(this.executeWhereResult)) {
//				// 处理普通取值
//				for (ExecuteLazy executeLazy : this.queryCache) {
//					if (!executeLazy.isGroup()) {
//						executeLazy.duQuery(this.queryReusltDataItem);
//					}
//				}
//
//				boolean isExistsInGroup = false;
//				// 如果是group操作,获取分组,并判断是否需要将当前取值添加到集合中去
//				if (this.isGroup) {
//					TwoTuple<Boolean, Long> putQueryResultDataItem = this.queryExecutor.getGroupExecutor()
//							.putQueryResultDataItem(queryReusltDataItem);
//					isExistsInGroup = putQueryResultDataItem.first;
//					this.queryReusltDataItem.setGroupId(putQueryResultDataItem.second);
//				}
//				// 处理分组聚合函数
//				for (ExecuteLazy executeLazy : this.queryCache) {
//					if (executeLazy.isGroup()) {
//						executeLazy.duQuery(this.queryReusltDataItem);
//					}
//				}
//				if (this.isGroup) {
//					GroupExecutor groupExecutor = this.queryExecutor.getGroupExecutor();
//					groupExecutor.doHaving(this.queryReusltDataItem);
//				}
//				// 根据分组情况判断当前查询结果是否添加到最终结果集合中
//				if (!isExistsInGroup) {
//					long allocateRecordId = this.queryExecutor.getDataCache().allocateRecordId();
//					this.queryReusltDataItem.setRecordId(allocateRecordId);
//					this.queryExecutor.getDataCache().cacheRecord(this.queryReusltDataItem);
//					this.queryReusltDataItem.getRecordId();
//				}
//				// 处理排序
//				if (this.queryExecutor.getOrderExecutor() != null && this.orderByDataItemWithObj != null) {
//					this.orderByDataItemWithObj.setRecordId(this.queryReusltDataItem.getRecordId());
//					this.queryExecutor.getOrderExecutor().addOrderByDataItem(orderByDataItemWithObj);
//				}
//			}
//			this.queryCache.clear();
//			this.queryCache = null;
//		} catch (Exception e) {
//			e.printStackTrace();
//			// System.out.println("skip!!!");
//		}
//	}
//
//	@Override
//	public void enterArrayPart(ArrayPartContext ctx) {
//		log.debug("{} and currentPath:{}", "enterArrayPart", this.currentScope.getScopeName());
//		this.currentScope = new ArrayScope(this.currentScope, "");
//	}
//
//	@Override
//	public void exitArrayPart(ArrayPartContext ctx) {
//		log.debug("{} and currentPath:{}", "exitArrayPart", this.currentScope.getScopeName());
//		doQueryInvoke();
//		this.currentScope = this.currentScope.getEnclosingScope();
//		this.config();
//	}
//
//	@Override
//	public void enterKeyValue(KeyValueContext ctx) {
//		log.debug("{} and currentPath:{}", "enterKeyValue", this.currentScope.getScopeName());
//		this.currentScope = new ObjPairKeyValueScope(this.currentScope, StrUtils.removeCommon(ctx.STRING().getText()));
//		this.invokeJsonDataQuery(this.currentScope.getScopeName() + ".*", ctx.getText(), true);
//	}
//
//	@Override
//	public void exitKeyValue(KeyValueContext ctx) {
//		log.debug("{} and currentPath:{}", "exitKeyValue", this.currentScope.getScopeName());
//		this.currentScope = this.currentScope.getEnclosingScope();
//	}
//
//	@Override
//	public void exitStringValue(StringValueContext ctx) {
//		log.debug("{} and currentPath:{}", "exitStringValue", this.currentScope.getScopeName());
//		String text = ctx.STRING().getText();
//		if (this.currentScope instanceof ObjPairKeyValueScope) {
//			this.invokeOrderBy(this.currentScope.getScopeName(), StrUtils.removeCommon(text));
//		}
//		invokeJsonDataQuery(this.currentScope.getScopeName(), StrUtils.removeCommon(text));
//		invokeJsonDataCondition(this.currentScope.getScopeName(), StrUtils.removeCommon(text));
//	}
//
//	@Override
//	public void exitNumberValue(NumberValueContext ctx) {
//		log.debug("{} and currentPath:{}", "exitNumberValue", this.currentScope.getScopeName());
//		TerminalNode number = ctx.NUMBER();
//		String text = number.getText();
//		BigDecimal numberBig = null;
//		Object numValue = null;
//		numberBig = new BigDecimal(text);
//		log.debug("valveText:{}", text);
//		if (text.contains(".")) {
//			numValue = numberBig.doubleValue();
//		} else {
//			numValue = numberBig.intValue();
//		}
//		if (this.currentScope instanceof ObjPairKeyValueScope) {
//			this.invokeOrderBy(this.currentScope.getScopeName(), numValue);
//		}
//		invokeJsonDataQuery(this.currentScope.getScopeName(), numValue);
//		invokeJsonDataCondition(this.currentScope.getScopeName(), numValue);
//	}
//
//	@Override
//	public void exitTrueValue(TrueValueContext ctx) {
//		log.debug("{} and currentPath:{}", "exitTrueValue", this.currentScope.getScopeName());
//		if (this.currentScope instanceof ObjPairKeyValueScope) {
//			this.invokeOrderBy(this.currentScope.getScopeName(), true);
//		}
//		invokeJsonDataQuery(this.currentScope.getScopeName(), true);
//		invokeJsonDataCondition(this.currentScope.getScopeName(), true);
//	}
//
//	@Override
//	public void exitFalseValue(FalseValueContext ctx) {
//		log.debug("{} and currentPath:{}", "exitFalseValue", this.currentScope.getScopeName());
//		if (this.currentScope instanceof ObjPairKeyValueScope) {
//			this.invokeOrderBy(this.currentScope.getScopeName(), false);
//		}
//		invokeJsonDataQuery(this.currentScope.getScopeName(), false);
//		invokeJsonDataCondition(this.currentScope.getScopeName(), false);
//	}
//
//	@Override
//	public void exitNullValue(NullValueContext ctx) {
//		log.debug("{} and currentPath:{}", "exitNullValue", this.currentScope.getScopeName());
//		if (this.currentScope instanceof ObjPairKeyValueScope) {
//			this.invokeOrderBy(this.currentScope.getScopeName(), null);
//		}
//		invokeJsonDataQuery(this.currentScope.getScopeName(), null);
//		invokeJsonDataCondition(this.currentScope.getScopeName(), null);
//	}
//
//}
