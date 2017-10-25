package com.extensions.logmonitor.jsonContentParseies.copy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.ArrayPartContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.ArrayValuesContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.EmptyArrayContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.EmptyObjContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.FalseValueContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.KeyValueContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.NullValueContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.NumberValueContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.ObjPairContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.ObjectPartContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.StringValueContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.SubArrayContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.SubObjectContext;
import com.extensions.logmonitor.jsonContentParseies.copy.jsonParser.TrueValueContext;
import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute.QueryExecutorJsonWalker;
import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope.JsonSuperScope;
import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope.ObjPairKeyValueScope;
import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope.ObjectScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group.GroupExecutor;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderExecutor;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.Scope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.SelectPart;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;
import com.extensions.logmonitor.util.StrUtils;
import com.extensions.logmonitor.util.TupleUtil;
import com.extensions.logmonitor.util.TwoTuple;
import com.google.common.collect.Sets;

/**
 * 
 * @say little Boy, don\"t be sad.
 * @name Rezar
 * @time 2017年10月25日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class JsonContentVisitor extends jsonBaseVisitor<Void> {

	private JsonSuperScope jsonSuperScope;
	private Scope currentScope;

	private List<QueryExecutorJsonWalker> queryExecutorWalker;
	private Set<String> antrlParseFieldPaths = Sets.newHashSet();

	public JsonContentVisitor(List<QueryExecutor> queryExecutors) {
		this.queryExecutorWalker = new ArrayList<>(queryExecutors.size());
		for (QueryExecutor qe : queryExecutors) {
			takeParseFieldPaths(qe);
			QueryExecutorJsonWalker walker = new QueryExecutorJsonWalker(qe);
			queryExecutorWalker.add(walker);
		}
		this.config();
	}

	/**
	 * @param qe
	 */
	private void takeParseFieldPaths(QueryExecutor qe) {
		SelectPart selectPart = qe.getSelectPart();
		selectPart.fillParseFieldPaths(this.antrlParseFieldPaths);
		WhereCondition whereCondition = qe.getWhereCondition();
		if (whereCondition != null) {
			whereCondition.fillParseFieldPaths(this.antrlParseFieldPaths);
		}
		OrderExecutor orderExecutor = qe.getOrderExecutor();
		if (orderExecutor != null) {
			orderExecutor.fillParseFieldPaths(this.antrlParseFieldPaths);
		}
		GroupExecutor groupExecutor = qe.getGroupExecutor();
		if (groupExecutor != null) {
			groupExecutor.fillParseFieldPaths(this.antrlParseFieldPaths);
		}
	}

	public TwoTuple<Boolean, Boolean> checkNeedDoParse(String currentParsePath, boolean isSuper) {
		boolean currentNeedParse = false;
		boolean needParseChild = false;
		for (String needParsePath : this.antrlParseFieldPaths) {
			if (needParsePath.startsWith(currentParsePath)) {
				if (!currentNeedParse) {
					boolean isPathMatch = needParsePath.equals(currentParsePath)
							|| ((isSuper) && needParsePath.equals(currentParsePath + ".*"));
					if (isPathMatch) {
						currentNeedParse = true;
					}
				}
				if (!needParseChild) {
					if (needParsePath.length() > currentParsePath.length()) {
						needParseChild = true;
					}
				}
			}
			if (currentNeedParse && needParseChild) {
				break;
			}
		}
		if (!needParseChild) {
			if (currentParsePath.equals("*") && this.antrlParseFieldPaths.size() > 1) {
				needParseChild = true;
			}
		}
		return TupleUtil.tuple(currentNeedParse, needParseChild);
	}

	private void config() {
		this.jsonSuperScope = new JsonSuperScope();
		currentScope = jsonSuperScope;
		this.doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.config();
			}
		});
	}

	@Override
	public Void visitObjectPart(final ObjectPartContext ctx) {
		TwoTuple<Boolean, Boolean> checkNeedDoParse = this.checkNeedDoParse("*", true);
		if (checkNeedDoParse.first) {
			System.out.println(this.currentScope.getScopeName() + ".*");
			this.doInWalkers(new DoInWalker() {
				@Override
				public void walk(QueryExecutorJsonWalker walker) {
					walker.invokeJsonDataQuery("*", ctx.getText());
				}
			});
		}
		if (checkNeedDoParse.second) {
			currentScope = new ObjectScope(currentScope, "");
			super.visitChildren(ctx);
		}
		currentScope = currentScope.getEnclosingScope();
		this.config();
		return null;
	}

	@Override
	public Void visitArrayPart(ArrayPartContext ctx) {
		return super.visitArrayPart(ctx);
	}

	@Override
	public Void visitObjPair(ObjPairContext ctx) {
		return super.visitObjPair(ctx);
	}

	@Override
	public Void visitArrayValues(ArrayValuesContext ctx) {
		return null;
	}

	@Override
	public Void visitEmptyArray(EmptyArrayContext ctx) {
		return null;
	}

	@Override
	public Void visitEmptyObj(EmptyObjContext ctx) {
		System.out.println("visit empty obj");
		return null;
	}

	@Override
	public Void visitKeyValue(KeyValueContext ctx) {
		currentScope = new ObjPairKeyValueScope(currentScope, StrUtils.removeCommon(ctx.STRING().getText()));
		super.visitKeyValue(ctx);
		this.currentScope = this.currentScope.getEnclosingScope();
		return null;
	}

	@Override
	public Void visitSubObject(final SubObjectContext ctx) {
		TwoTuple<Boolean, Boolean> checkNeedDoParse = this.checkNeedDoParse(this.currentScope.getScopeName(), true);
		if (checkNeedDoParse.first) {
			System.out.println(this.currentScope.getScopeName() + ".*:" + "" + ctx.getText());
			this.doInWalkers(new DoInWalker() {
				@Override
				public void walk(QueryExecutorJsonWalker walker) {
					walker.invokeJsonDataQuery(currentScope.getScopeName() + ".*", ctx.getText());
				}
			});
		}
		if (checkNeedDoParse.second) {
			super.visitSubObject(ctx);
		}
		return null;
	}

	@Override
	public Void visitStringValue(StringValueContext ctx) {
		TwoTuple<Boolean, Boolean> checkNeedDoParse = this.checkNeedDoParse(this.currentScope.getScopeName(), false);
		if (checkNeedDoParse.first) {
			final String stringValue = StrUtils.removeCommon(ctx.STRING().getText());
			System.out.println(this.currentScope.getScopeName() + ":" + stringValue);
			doInWalkers(new DoInWalker() {
				@Override
				public void walk(QueryExecutorJsonWalker walker) {
					walker.invokeJsonDataQuery(currentScope.getScopeName(), stringValue);
					walker.invokeJsonDataCondition(currentScope.getScopeName(), stringValue);
				}
			});
		}
		return null;
	}

	@Override
	public Void visitNumberValue(NumberValueContext ctx) {
		TwoTuple<Boolean, Boolean> checkNeedDoParse = this.checkNeedDoParse(this.currentScope.getScopeName(), false);
		if (checkNeedDoParse.first) {
			String text = ctx.NUMBER().getText();
			BigDecimal numberBig = null;
			final AtomicReference<Object> numValue = new AtomicReference<Object>(null);
			numberBig = new BigDecimal(text);
			if (text.contains(".")) {
				numValue.set(numberBig.doubleValue());
			} else {
				numValue.set(numberBig.intValue());
			}
			System.out.println(this.currentScope.getScopeName() + ":" + numValue.get());
			doInWalkers(new DoInWalker() {
				@Override
				public void walk(QueryExecutorJsonWalker walker) {
					walker.invokeJsonDataQuery(currentScope.getScopeName(), numValue.get());
					walker.invokeJsonDataCondition(currentScope.getScopeName(), numValue.get());
				}
			});
		}
		return null;
	}

	@Override
	public Void visitSubArray(SubArrayContext ctx) {
		// TODO
		return null;
	}

	@Override
	public Void visitFalseValue(FalseValueContext ctx) {
		TwoTuple<Boolean, Boolean> checkNeedDoParse = this.checkNeedDoParse(this.currentScope.getScopeName(), false);
		if (checkNeedDoParse.first) {
			System.out.println(this.currentScope.getScopeName() + ":" + false);
			doInWalkers(new DoInWalker() {
				@Override
				public void walk(QueryExecutorJsonWalker walker) {
					walker.invokeJsonDataQuery(currentScope.getScopeName(), false);
					walker.invokeJsonDataCondition(currentScope.getScopeName(), false);
				}
			});
		}
		return null;
	}

	@Override
	public Void visitTrueValue(TrueValueContext ctx) {
		TwoTuple<Boolean, Boolean> checkNeedDoParse = this.checkNeedDoParse(this.currentScope.getScopeName(), false);
		if (checkNeedDoParse.first) {
			System.out.println(this.currentScope.getScopeName() + ":" + true);
			doInWalkers(new DoInWalker() {
				@Override
				public void walk(QueryExecutorJsonWalker walker) {
					walker.invokeJsonDataQuery(currentScope.getScopeName(), true);
					walker.invokeJsonDataCondition(currentScope.getScopeName(), true);
				}
			});
		}
		return null;
	}

	@Override
	public Void visitNullValue(NullValueContext ctx) {
		TwoTuple<Boolean, Boolean> checkNeedDoParse = this.checkNeedDoParse(this.currentScope.getScopeName(), false);
		if (checkNeedDoParse.first) {
			System.out.println(this.currentScope.getScopeName() + ": null");
			doInWalkers(new DoInWalker() {
				@Override
				public void walk(QueryExecutorJsonWalker walker) {
					walker.invokeJsonDataQuery(currentScope.getScopeName(), null);
					walker.invokeJsonDataCondition(currentScope.getScopeName(), null);
				}
			});
		}
		return null;
	}

	private static interface DoInWalker {
		public void walk(QueryExecutorJsonWalker walker);
	}

	public void doInWalkers(DoInWalker doInWalker) {
		for (QueryExecutorJsonWalker walker : this.queryExecutorWalker) {
			doInWalker.walk(walker);
		}
	}

}
