package com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.extensions.logmonitor.exceptions.IllegalFunCall;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.AliasContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Any_name_exclude_keywordContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Boolean_literalContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Column_specContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondBetweenOrNotContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondEqContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondGetContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondGtContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondInOrNotContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondIsTFNOrNotContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondLetContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondLikeOrNotContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondLtContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondNotEqContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondRegexpOrNotContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.CondSubCondsContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Exp_factor1Context;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Exp_factor2Context;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.ExpressionContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Expression_listContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Function_callContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Group_functionsContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Groupby_clauseContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Groupby_itemContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Having_clauseContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.LimitContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Literal_valueContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Number_literalContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.OffsetContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.OrderByContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Orderby_itemContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.PredicateContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.SelectAllContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.SelectFunContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.SelectTableColumnContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.SelectTableDotAllContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Select_expressionContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Select_listContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Simple_exprContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.String_literalContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Table_referencesContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.WhereAndContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.WhereOrContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.WhereXORContext;
import com.extensions.logmonitor.jsonLogModule.jsonLogSqlParseies.jsonLogSqlParser.Where_clauseContext;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderByItem;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderExecutor;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderType;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.FromScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.GroupScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.LimitScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.OrderByScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.Scope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.SelectScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.SelectSuperScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.WhereScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.AvgFunQueryExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.ColumnValueQueryExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.CountFunQueryExectue;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.MaxFunQueryExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.MinFunQueryExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.QueryExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.SelectPart;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.SumValueQueryExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.ConditionSuper;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.MultiCondition;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.OptAndCondition;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.OptExecute;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.OptOrCondition;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.OptXOrCondition;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.SingleCondition;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.BetweenOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.EqOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.GtOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.GteOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.InOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.IsNotNullOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.IsNotOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.IsNullOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.IsOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.LikeOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.LtOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.LteOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.NotBetweenOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.NotEqOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.NotInOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.NotLikeOpt;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;
import com.extensions.logmonitor.util.GenericsUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonLogSqlAnalyzer extends jsonLogSqlBaseListener {

	ParseTreeProperty<Scope> scope = new ParseTreeProperty<>();

	public QueryExecutor queryExecutor = new QueryExecutor();
	private Scope currentScope;

	public boolean isGroupWhereCondition; // 标识当前是否是group having 的where判断

	// ============================处理group by Start============================

	@Override
	public void enterGroupby_clause(Groupby_clauseContext ctx) {
		log.debug("enterGroupby_clause");
		this.queryExecutor.initGroupByExecutor();
		GroupScope groupScope = new GroupScope(this.currentScope, new WhereCondition());
		this.currentScope = groupScope;
		this.queryExecutor.getGroupExecutor().addGroupByCondition(groupScope.getWhereCondition());
	}

	@Override
	public void exitGroupby_clause(Groupby_clauseContext ctx) {
		log.debug("exitGroupby_clause");
		if (this.currentScope instanceof GroupScope) {
			List<Groupby_itemContext> groupby_item = ctx.groupby_item();
			for (Groupby_itemContext gic : groupby_item) {
				Column_specContext column_spec = gic.column_spec();
				if (column_spec != null) {
					this.queryExecutor.getGroupExecutor().addOrderByItem(column_spec.getText());
				}
			}
			log.debug("group by Items:{}", this.queryExecutor.getGroupExecutor().getGroupByItem());
			this.queryExecutor.getGroupExecutor()
					.addGroupByCondition(((GroupScope) this.currentScope).getWhereCondition());
		}
		this.currentScope = this.currentScope.getEnclosingScope();
	}

	@Override
	public void enterHaving_clause(Having_clauseContext ctx) {
		log.debug("enterHaving_clause");
		isGroupWhereCondition = true;
		this.queryExecutor.getGroupExecutor().setNeedHaving(true);
	}

	@Override
	public void exitHaving_clause(Having_clauseContext ctx) {
		log.debug("exitHaving_clause");
		isGroupWhereCondition = false;
		ExpressionContext ec = ctx.expression();
		WhereCondition whereCondition = ((GroupScope) this.currentScope).getWhereCondition();
		Scope multiCondition = this.scope.get(ec);
		if (multiCondition instanceof MultiCondition) {
			whereCondition.setCondition((MultiCondition) multiCondition).quickVisitOptExecute();
		}
		Map<String, List<OptExecute>> optExecuteQuickVisitCache = whereCondition.getOptExecuteQuickVisitCache();
		log.debug("group multiCondition is:{} ", optExecuteQuickVisitCache);
	}

	// ============================处理group by end============================

	// ============================处理order by Start============================

	@Override
	public void enterOrderBy(OrderByContext ctx) {
		this.queryExecutor.setOrderExecutor(new OrderExecutor());
		this.currentScope = new OrderByScope(this.currentScope);
	}

	@Override
	public void exitOrderBy(OrderByContext ctx) {
		List<Orderby_itemContext> orderby_item = ctx.orderby_item();
		for (Orderby_itemContext orderByItemC : orderby_item) {
			Groupby_itemContext groupby_item = orderByItemC.groupby_item();
			String orderByPath = groupby_item.getText();
			TerminalNode desc = orderByItemC.DESC();
			OrderType orderType = OrderType.ASC;
			if (desc != null) {
				orderType = OrderType.DESC;
			}
			OrderByItem orderByItem = new OrderByItem();
			orderByItem.setOrderByPath(orderByPath);
			orderByItem.setOrderType(orderType);
			log.debug("orderByStr:{}", orderByPath);
			this.queryExecutor.getOrderExecutor().addOrderByItem(orderByItem);
		}
		this.currentScope = this.currentScope.getEnclosingScope();
	}

	// ============================处理order by end============================

	// ============================处理limit Start============================

	@Override
	public void enterLimit(LimitContext ctx) {
		this.currentScope = new LimitScope(this.currentScope);
	}

	@Override
	public void exitLimit(LimitContext ctx) {
		Long[] limitInfos = new Long[2];
		OffsetContext offset = ctx.offset();
		Long offsetValue = 0l;
		if (offset != null) {
			TerminalNode offsetNumValueNode = offset.INTEGER_NUM();
			offsetValue = Long.parseLong(offsetNumValueNode.getText());
		}
		limitInfos[0] = offsetValue;
		TerminalNode batchSizeNode = ctx.row_count().INTEGER_NUM();
		Long batchSize = 0l;
		if (batchSizeNode != null) {
			batchSize = Long.parseLong(batchSizeNode.getText());
		}
		limitInfos[1] = batchSize;
		log.debug("limitInfos are:{}-{} ", limitInfos[0], limitInfos[1]);
		this.queryExecutor.setLimitInfos(limitInfos);
	}

	// =================处理limit end============

	// ===========处理select list Start==========

	@Override
	public void enterSelect_expression(Select_expressionContext ctx) {
		this.currentScope = new SelectSuperScope();
	}

	@Override
	public void enterSelect_list(Select_listContext ctx) {
		log.debug("enterSelect_list");
		this.currentScope = new SelectScope(this.currentScope, new SelectPart());
	}

	@Override
	public void exitSelect_list(Select_listContext ctx) {
		log.debug("exitSelect_list");
		if (this.currentScope instanceof SelectScope) {
			this.queryExecutor.setSelectPart(((SelectScope) this.currentScope).getSelectPart());
		}
		this.currentScope = this.currentScope.getEnclosingScope();

	}

	@Override
	public void enterSelectAll(SelectAllContext ctx) {
		log.debug("enterSelectAll");
	}

	@Override
	public void exitSelectAll(SelectAllContext ctx) {
		log.debug("exitSelectAll");
		if (this.currentScope instanceof SelectScope) {
			SelectPart selectPart = ((SelectScope) this.currentScope).getSelectPart();
			QueryExecute<? extends Object> queryExecute = new ColumnValueQueryExecute();
			queryExecute.setQueryPath("*");
			selectPart.addQueryExecute(queryExecute);
		}
	}

	@Override
	public void enterSelectTableDotAll(SelectTableDotAllContext ctx) {
		log.debug("enterSelectTableDotAll");
	}

	@Override
	public void exitSelectTableDotAll(SelectTableDotAllContext ctx) {
		log.debug("exitSelectTableDotAll");
		if (this.currentScope instanceof SelectScope) {
			SelectPart selectPart = ((SelectScope) this.currentScope).getSelectPart();
			QueryExecute<? extends Object> queryExecute = new ColumnValueQueryExecute();
			queryExecute.setQueryPath(ctx.getText());
			selectPart.addQueryExecute(queryExecute);
		}
	}

	@Override
	public void enterSelectTableColumn(SelectTableColumnContext ctx) {
		log.debug("enterSelectTableColumn");
	}

	@Override
	public void exitSelectTableColumn(SelectTableColumnContext ctx) {
		log.debug("exitSelectTableColumn");
		if (this.currentScope instanceof SelectScope) {
			SelectPart selectPart = ((SelectScope) this.currentScope).getSelectPart();
			QueryExecute<? extends Object> queryExecute = new ColumnValueQueryExecute();
			queryExecute.setQueryPath(ctx.getText());
			String aliasStr = getAlias(ctx.alias());
			queryExecute.setAlias(aliasStr);
			selectPart.addQueryExecute(queryExecute);
		}
	}

	/**
	 * @param ctx
	 * @param aliasStr
	 * @return
	 */
	private String getAlias(AliasContext alias) {
		if (alias != null) {
			Any_name_exclude_keywordContext any_name_exclude_keyword = alias.any_name_exclude_keyword();
			return any_name_exclude_keyword.getText();
		}
		return null;
	}

	@Override
	public void enterSelectFun(SelectFunContext ctx) {
		log.debug("enterSelectFun");
	}

	@Override
	public void exitSelectFun(SelectFunContext ctx) {
		log.debug("exitSelectFun");
		if (this.currentScope instanceof SelectScope) {
			log.debug("exitSelectFun1");
			SelectPart selectPart = ((SelectScope) this.currentScope).getSelectPart();
			Function_callContext function_call = ctx.function_call();
			Group_functionsContext groupFun = function_call.group_functions();
			if (groupFun != null) {
				log.debug("exitSelectFun2");
				QueryExecute<? extends Object> qe = null;
				if (groupFun.COUNT() != null) {
					qe = new CountFunQueryExectue();
					TerminalNode distinct = function_call.DISTINCT();
					if (distinct != null) {
						((CountFunQueryExectue) qe).setDistinct(true);
					}
				} else if (groupFun.SUM() != null) {
					qe = new SumValueQueryExecute();
				} else if (groupFun.MAX() != null) {
					qe = new MaxFunQueryExecute();
				} else if (groupFun.MIN() != null) {
					qe = new MinFunQueryExecute();
				} else if (groupFun.AVG() != null) {
					qe = new AvgFunQueryExecute();
				} else {
					log.warn("unsupport function call:{} ", ctx.getText());
					return;
				}
				Simple_exprContext simple_expr = function_call.simple_expr();
				if (simple_expr != null) {
					qe.setQueryPath(simple_expr.getText());
				} else {
					qe.setQueryPath("*");
				}
				qe.setAlias(getAlias(ctx.alias()));
				log.debug("QueryExecute is:{} {}", qe, qe.getQueryPathWithFieldName());
				selectPart.addFunQueryExecute(qe);
			}
		}
	}

	// ============================处理select list end============================

	// ============================处理fromTable Start============================

	@Override
	public void enterTable_references(Table_referencesContext ctx) {
		log.debug("enterTable_references");
		FromScope fromScope = new FromScope(this.currentScope);
		this.currentScope = fromScope;
		queryExecutor.setFromTableLogName(ctx.getText());
	}

	@Override
	public void exitTable_references(Table_referencesContext ctx) {
		this.currentScope = this.currentScope.getEnclosingScope();
		log.debug("exitTable_references");
	}
	// ============================处理fromTable END ============================

	// ============================处理Where Start============================
	@Override
	public void enterWhere_clause(Where_clauseContext ctx) {
		log.debug("enterWhere_clause");
		WhereScope whereScope = new WhereScope(this.currentScope, new WhereCondition());
		this.currentScope = whereScope;
		this.queryExecutor.setWhereCondition(whereScope.getWhereCondition());
	}

	@Override
	public void exitWhere_clause(Where_clauseContext ctx) {
		log.debug("exitWhere_clause");
		if (this.currentScope instanceof WhereScope) {
			ExpressionContext ec = ctx.expression();
			WhereCondition whereCondition = ((WhereScope) this.currentScope).getWhereCondition();
			Scope multiCondition = this.scope.get(ec);
			if (multiCondition instanceof MultiCondition) {
				whereCondition.setCondition((MultiCondition) multiCondition).quickVisitOptExecute();
			}
			Map<String, List<OptExecute>> optExecuteQuickVisitCache = whereCondition.getOptExecuteQuickVisitCache();
			log.debug("multiCondition is:{} ", optExecuteQuickVisitCache);
		}
		this.currentScope = this.currentScope.getEnclosingScope();
	}
	// ============================处理Where END ============================

	@Override
	public void enterWhereOr(WhereOrContext ctx) {
		log.debug("enterWhereOr");
		if (this.currentScope instanceof WhereScope || this.currentScope instanceof GroupScope
				|| this.currentScope instanceof MultiCondition) {
			MultiCondition multiCondition = new MultiCondition(this.currentScope);
			this.currentScope = multiCondition;
			this.scope.put(ctx, multiCondition);
		}
	}

	@Override
	public void exitWhereOr(WhereOrContext ctx) {
		log.debug("exitWhereOr");
		if (this.currentScope instanceof MultiCondition) {
			List<Exp_factor1Context> exp_factor1 = ctx.exp_factor1();
			MultiCondition condition = (MultiCondition) this.currentScope;
			for (Exp_factor1Context efc : exp_factor1) {
				Scope multiCondition = this.scope.get(efc);
				if (multiCondition instanceof MultiCondition) {
					log.debug("multiCondition is:{} ", multiCondition);
					condition.addCondition((MultiCondition) multiCondition).addCondition(new OptOrCondition(condition));
				}
			}
			if (GenericsUtils.notNullAndEmpty(condition.getConditions())) {
				condition.getConditions().remove(condition.getConditions().size() - 1);
			}
		}
		this.currentScope = this.currentScope.getEnclosingScope();
	}

	@Override
	public void enterWhereXOR(WhereXORContext ctx) {
		log.debug("enterWhereXOR");
		if (this.currentScope instanceof MultiCondition) {
			MultiCondition multiCondition = new MultiCondition(this.currentScope);
			this.currentScope = multiCondition;
			this.scope.put(ctx, multiCondition);
		}
	}

	@Override
	public void exitWhereXOR(WhereXORContext ctx) {
		log.debug("exitWhereXOR");
		if (this.currentScope instanceof MultiCondition) {
			List<Exp_factor2Context> exp_factor2 = ctx.exp_factor2();
			MultiCondition condition = (MultiCondition) this.currentScope;
			for (Exp_factor2Context efc : exp_factor2) {
				Scope multiCondition = this.scope.get(efc);
				if (multiCondition instanceof MultiCondition) {
					log.debug("multiCondition is:{} ", multiCondition);
					condition.addCondition((MultiCondition) multiCondition)
							.addCondition(new OptXOrCondition(condition));
				}
			}
			if (GenericsUtils.notNullAndEmpty(condition.getConditions())) {
				condition.getConditions().remove(condition.getConditions().size() - 1);
			}
		}
		this.currentScope = this.currentScope.getEnclosingScope();
	}

	@Override
	public void enterWhereAnd(WhereAndContext ctx) {
		log.debug("enterWhereAnd");
		if (this.currentScope instanceof MultiCondition) {
			MultiCondition multiCondition = new MultiCondition(currentScope);
			this.currentScope = multiCondition;
			this.scope.put(ctx, multiCondition);
		}
	}

	@Override
	public void exitWhereAnd(WhereAndContext ctx) {
		log.debug("exitWhereAnd");
		if (this.currentScope instanceof MultiCondition) {
			List<PredicateContext> predicate = ctx.predicate();
			MultiCondition condition = (MultiCondition) this.currentScope;
			for (PredicateContext pc : predicate) {
				Scope singleCondition = this.scope.get(pc);
				if (singleCondition instanceof ConditionSuper) {
					log.debug("ConditionSuper:{}", singleCondition);
					condition.addCondition((ConditionSuper) singleCondition)
							.addCondition(new OptAndCondition(condition));
				}
			}
			if (GenericsUtils.notNullAndEmpty(condition.getConditions())) {
				condition.getConditions().remove(condition.getConditions().size() - 1);
			}
		}
		this.currentScope = this.currentScope.getEnclosingScope();
	}

	/**
	 * 暂时只支持optField/groupFun optType value
	 * 
	 * @param simple_expr
	 * @return
	 */
	private String createMatchPathAndValue(Simple_exprContext simple_expr, OptExecute optExecute) {
		String matchPath = "";
		List<ParseTree> children = simple_expr.children;
		for (ParseTree pt : children) {
			if (pt instanceof Function_callContext) {
				Function_callContext fc = (Function_callContext) pt;
				matchPath = handleFunctionCall(fc, optExecute).toString();
			} else if (pt instanceof Column_specContext) {
				Column_specContext cpc = (Column_specContext) pt;
				matchPath = handleColumnSpace(cpc).toString();
			}
		}
		return matchPath;
	}

	private Object getValueFromSimpleExpre(Simple_exprContext simple_expr) {
		Object matchValue = null;
		List<ParseTree> children = simple_expr.children;
		for (ParseTree pt : children) {
			if (pt instanceof Function_callContext) {
				Function_callContext fc = (Function_callContext) pt;
				throw new IllegalFunCall("value can not be a function call : " + fc.getText());
			} else if (pt instanceof Column_specContext) {
				throw new IllegalFunCall("value can not be a function call : " + pt.getText());
			} else if (pt instanceof Literal_valueContext) {
				matchValue = getMatchValue((Literal_valueContext) pt);
			}
		}
		return matchValue;
	}

	/**
	 * @param cpc
	 * @return
	 */
	private Object handleColumnSpace(Column_specContext cpc) {
		return OptExecute.COLUMN_NAME_PREFIX + cpc.getText();
	}

	/**
	 * @param fc
	 * @param optExecute
	 * @return
	 */
	private Object handleFunctionCall(Function_callContext fc, OptExecute optExecute) {
		if (!this.isGroupWhereCondition) {
			throw new IllegalFunCall("not support group functioin call in where condition:" + fc.getText());
		}
		Group_functionsContext groupFun = fc.group_functions();
		if (groupFun != null) {
			log.debug("exitSelectFun2");
			QueryExecute<? extends Object> qe = null;
			if (groupFun.COUNT() != null) {
				qe = new CountFunQueryExectue();
				TerminalNode distinct = fc.DISTINCT();
				if (distinct != null) {
					((CountFunQueryExectue) qe).setDistinct(true);
				}
			} else if (groupFun.SUM() != null) {
				qe = new SumValueQueryExecute();
			} else if (groupFun.MAX() != null) {
				qe = new MaxFunQueryExecute();
			} else if (groupFun.MIN() != null) {
				qe = new MinFunQueryExecute();
			} else if (groupFun.AVG() != null) {
				qe = new AvgFunQueryExecute();
			} else {
				log.warn("unsupport function call:{} ", fc.getText());
			}
			Simple_exprContext simple_expr = fc.simple_expr();
			if (simple_expr != null) {
				qe.setQueryPath(simple_expr.getText());
			} else {
				qe.setQueryPath("*");
			}
			// log.info("Group QueryExecute is:{} {}", qe,
			// qe.getQueryPathWithFieldName());
			this.queryExecutor.getGroupExecutor().addGroupFunQuery(optExecute, qe);
			return OptExecute.FUN_CALL_PREFIX + qe.getQueryPathWithFieldName();
		}
		return null;
	}

	@Override
	public void enterCondEq(CondEqContext ctx) {
		log.debug("enterCondEq");
		List<Simple_exprContext> simple_expr = ctx.simple_expr();
		Object matchValue = getValueFromSimpleExpre(simple_expr.get(1));
		EqOpt eqOpt = new EqOpt(matchValue);
		eqOpt.setMatchPath(createMatchPathAndValue(simple_expr.get(0), eqOpt));
		log.debug("eqOpt is:{} ", eqOpt);
		this.scope.put(ctx, new SingleCondition(this.currentScope, eqOpt));
	}

	@Override
	public void enterCondNotEq(CondNotEqContext ctx) {
		log.debug("enterCondNotEq");
		List<Simple_exprContext> simple_expr = ctx.simple_expr();
		Object matchValue = getValueFromSimpleExpre(simple_expr.get(1));
		NotEqOpt notEqOpt = new NotEqOpt(matchValue);
		notEqOpt.setMatchPath(createMatchPathAndValue(simple_expr.get(0), notEqOpt));
		this.scope.put(ctx, new SingleCondition(this.currentScope, notEqOpt));
	}

	@Override
	public void enterCondLt(CondLtContext ctx) {
		log.debug("enterCondLt");
		List<Simple_exprContext> simple_expr = ctx.simple_expr();
		Object matchValue = getValueFromSimpleExpre(simple_expr.get(1));
		LtOpt ltOpt = new LtOpt(matchValue);
		ltOpt.setMatchPath(createMatchPathAndValue(simple_expr.get(0), ltOpt));
		this.scope.put(ctx, new SingleCondition(this.currentScope, ltOpt));
	}

	@Override
	public void enterCondGt(CondGtContext ctx) {
		log.debug("enterCondGt");
		List<Simple_exprContext> simple_expr = ctx.simple_expr();
		Object matchValue = getValueFromSimpleExpre(simple_expr.get(1));
		GtOpt gtOpt = new GtOpt(matchValue);
		gtOpt.setMatchPath(createMatchPathAndValue(simple_expr.get(0), gtOpt));
		this.scope.put(ctx, new SingleCondition(this.currentScope, gtOpt));
	}

	@Override
	public void enterCondLet(CondLetContext ctx) {
		log.debug("enterCondLet");
		List<Simple_exprContext> simple_expr = ctx.simple_expr();
		Object matchValue = getValueFromSimpleExpre(simple_expr.get(1));
		LteOpt lteOpt = new LteOpt(matchValue);
		lteOpt.setMatchPath(createMatchPathAndValue(simple_expr.get(0), lteOpt));
		this.scope.put(ctx, new SingleCondition(this.currentScope, lteOpt));
	}

	@Override
	public void enterCondGet(CondGetContext ctx) {
		log.debug("enterCondGet");
		List<Simple_exprContext> simple_expr = ctx.simple_expr();
		Object matchValue = getValueFromSimpleExpre(simple_expr.get(1));
		GteOpt gteOpt = new GteOpt(matchValue);
		gteOpt.setMatchPath(createMatchPathAndValue(simple_expr.get(0), gteOpt));
		this.scope.put(ctx, new SingleCondition(this.currentScope, gteOpt));
	}

	@Override
	public void enterCondInOrNot(CondInOrNotContext ctx) {
		log.debug("enterCondInOrNot");
		TerminalNode not = ctx.NOT();
		boolean isNot = false;
		if (not != null) {
			isNot = true;
		}
		List<Literal_valueContext> literal_value = ctx.literal_value();
		List<Object> matchValue = new ArrayList<>(literal_value.size());
		for (Literal_valueContext lv : literal_value) {
			Object value = getMatchValue(lv);
			matchValue.add(value);
		}
		OptExecute optExecute = null;
		if (isNot) {
			optExecute = new NotInOpt(matchValue);
		} else {
			optExecute = new InOpt(matchValue);
		}
		this.scope.put(ctx, new SingleCondition(this.currentScope, optExecute));
		optExecute.setMatchPath(createMatchPathAndValue(ctx.simple_expr(), optExecute));
	}

	@Override
	public void enterCondBetweenOrNot(CondBetweenOrNotContext ctx) {
		log.debug("enterCondBetweenOrNot");
		TerminalNode not = ctx.NOT();
		boolean isNot = false;
		if (not != null) {
			isNot = true;
		}
		List<Literal_valueContext> literal_value = ctx.literal_value();
		List<Object> matchValue = new ArrayList<>(literal_value.size());
		for (Literal_valueContext lv : literal_value) {
			Object value = getMatchValue(lv);
			matchValue.add(value);
		}
		if (isNot) {
			NotBetweenOpt notInOpt = new NotBetweenOpt(matchValue);
			notInOpt.setMatchPath(createMatchPathAndValue(ctx.simple_expr(), notInOpt));
			this.scope.put(ctx, new SingleCondition(this.currentScope, notInOpt));
		} else {
			BetweenOpt inOpt = new BetweenOpt(matchValue);
			inOpt.setMatchPath(createMatchPathAndValue(ctx.simple_expr(), inOpt));
			this.scope.put(ctx, new SingleCondition(this.currentScope, inOpt));
		}
	}

	@Override
	public void enterCondLikeOrNot(CondLikeOrNotContext ctx) {
		log.debug("enterCondLikeOrNot");
		TerminalNode not = ctx.NOT();
		boolean isNot = false;
		if (not != null) {
			isNot = true;
		}
		List<Simple_exprContext> simple_expr = ctx.simple_expr();
		Object matchValue = getValueFromSimpleExpre(simple_expr.get(1));
		if (isNot) {
			NotLikeOpt notLikeOpt = new NotLikeOpt(matchValue);
			notLikeOpt.setMatchPath(createMatchPathAndValue(simple_expr.get(0), notLikeOpt));
			this.scope.put(ctx, new SingleCondition(this.currentScope, notLikeOpt));
		} else {
			LikeOpt likeOpt = new LikeOpt(matchValue);
			likeOpt.setMatchPath(createMatchPathAndValue(simple_expr.get(0), likeOpt));
			this.scope.put(ctx, new SingleCondition(this.currentScope, likeOpt));
		}
	}

	@Override
	public void enterCondRegexpOrNot(CondRegexpOrNotContext ctx) {
		log.debug("enterCondRegexpOrNot");
	}

	@Override
	public void enterCondIsTFNOrNot(CondIsTFNOrNotContext ctx) {
		log.debug("enterCondIsTFNOrNot:{}", ctx.getText());
		boolean isNot = false;
		TerminalNode not = ctx.NOT();
		if (not != null) {
			isNot = true;
		}
		Boolean_literalContext boolean_literal = ctx.boolean_literal();
		boolean isBool = true;
		if (boolean_literal == null) {
			isBool = false;
		}
		if (!isNot) {
			if (isBool) {
				IsOpt isOpt = new IsOpt(boolean_literal.getText());
				isOpt.setMatchPath(createMatchPathAndValue(ctx.simple_expr(), isOpt));
				this.scope.put(ctx, new SingleCondition(this.currentScope, isOpt));
			} else {
				IsNullOpt isNullOpt = new IsNullOpt(null);
				isNullOpt.setMatchPath(createMatchPathAndValue(ctx.simple_expr(), isNullOpt));
				this.scope.put(ctx, new SingleCondition(this.currentScope, isNullOpt));
			}
		} else {
			if (isBool) {
				IsNotOpt isNotOpt = new IsNotOpt(boolean_literal.getText());
				isNotOpt.setMatchPath(createMatchPathAndValue(ctx.simple_expr(), isNotOpt));
				this.scope.put(ctx, new SingleCondition(this.currentScope, isNotOpt));
			} else {
				IsNotNullOpt isNotNullOpt = new IsNotNullOpt(null);
				isNotNullOpt.setMatchPath(createMatchPathAndValue(ctx.simple_expr(), isNotNullOpt));
				this.scope.put(ctx, new SingleCondition(this.currentScope, isNotNullOpt));
			}
		}
	}

	@Override
	public void enterCondSubConds(CondSubCondsContext ctx) {
		log.debug("enterCondSubConds");
		if (this.currentScope instanceof MultiCondition) {
			MultiCondition multiCondition = new MultiCondition(currentScope);
			this.currentScope = multiCondition;
			log.debug(" ==== ctx instance {}", ctx instanceof PredicateContext);
			this.scope.put(ctx, multiCondition);
		}
	}

	@Override
	public void exitCondSubConds(CondSubCondsContext ctx) {
		log.debug("exitCondSubConds");
		Expression_listContext expression_list = ctx.expression_list();
		List<ExpressionContext> expression = expression_list.expression();
		MultiCondition condition = (MultiCondition) this.currentScope;
		for (ExpressionContext ec : expression) {
			Scope multiCondition = this.scope.get(ec);
			log.debug("------- multiCondition is:{} ", multiCondition);
			if (multiCondition instanceof MultiCondition) {
				condition.addCondition((MultiCondition) multiCondition);
			}
		}
		this.currentScope = this.currentScope.getEnclosingScope();
	}

	private Object getMatchValue(Literal_valueContext lvc) {
		Number_literalContext number_literal = lvc.number_literal();
		if (number_literal != null) {
			if (number_literal.REAL_NUMBER() != null) {
				return new Float(number_literal.getText());
			} else {
				return new Integer(number_literal.getText());
			}
		}
		String_literalContext string_literal = lvc.string_literal();
		if (string_literal != null) {
			String text = string_literal.TEXT_STRING().getText();
			if (text.indexOf("\"") == 0 || text.indexOf("'") == 0) {
				text = text.substring(1, text.length()); // 去掉第一个 "
			}
			if (text.lastIndexOf("\"") == (text.length() - 1) || text.lastIndexOf("'") == (text.length() - 1)) {
				text = text.substring(0, text.length() - 1); // 去掉最后一个 "
			}
			return text;
		}
		Boolean_literalContext boolean_literal = lvc.boolean_literal();
		if (boolean_literal != null) {
			return new Boolean(boolean_literal.getText());
		}
		log.debug("can not find any value with lvc :{}", lvc.getText());
		return null;
	}

}
