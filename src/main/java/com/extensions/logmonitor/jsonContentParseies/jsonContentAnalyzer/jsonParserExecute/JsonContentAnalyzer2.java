package com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonBaseListener;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.ArrayPartContext;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.ArrayValuesContext;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.FalseValueContext;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.KeyValueContext;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.NullValueContext;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.NumberValueContext;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.ObjPairContext;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.ObjectPartContext;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.StringValueContext;
import com.extensions.logmonitor.jsonContentParseies.jsonAntlr4Parser.jsonParser.TrueValueContext;
import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute.QueryExecutorJsonWalker;
import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope.ArrayScope;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonContentAnalyzer2 extends jsonBaseListener {

	ParseTreeProperty<Scope> scope;
	private JsonSuperScope jsonSuperScope;
	private Scope currentScope;

	private List<QueryExecutorJsonWalker> queryExecutorWalker;
	// private static StopJsonContentParseException stopException = new
	// StopJsonContentParseException();
	private Set<String> antrlParseFieldPaths = Sets.newHashSet();

	public JsonContentAnalyzer2(List<QueryExecutor> queryExecutors) {
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
		this.scope = new ParseTreeProperty<>();
		this.jsonSuperScope = new JsonSuperScope();
		currentScope = jsonSuperScope;
		this.doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.config();
			}
		});
	}

	private static interface DoInWalker {
		public void walk(QueryExecutorJsonWalker walker);
	}

	public void doInWalkers(DoInWalker doInWalker) {
		for (QueryExecutorJsonWalker walker : this.queryExecutorWalker) {
			doInWalker.walk(walker);
		}
	}

	@Override
	public void enterObjectPart(final ObjectPartContext ctx) {
		log.debug("{} and currentPath:{}", "enterObjectPart", currentScope.getScopeName());
		TwoTuple<Boolean, Boolean> checkNeedDoParse = this.checkNeedDoParse("*", true);
		if (checkNeedDoParse.first && currentScope instanceof JsonSuperScope) {
			doInWalkers(new DoInWalker() {
				@Override
				public void walk(QueryExecutorJsonWalker walker) {
					walker.invokeJsonDataQuery("*", ctx.getText());
				}
			});
		} else if (checkNeedDoParse.second) {
			currentScope = new ObjectScope(currentScope, "");
		}
	}

	@Override
	public void exitObjectPart(final ObjectPartContext ctx) {
		log.debug("{} and currentPath:{}", "exitObjectPart", currentScope.getScopeName());
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.doQueryInvoke();
			}
		});
		currentScope = currentScope.getEnclosingScope();
		this.config();
	}

	@Override
	public void enterArrayPart(final ArrayPartContext ctx) {
		log.debug("{} and currentPath:{}", "enterArrayPart", currentScope.getScopeName());
		if (currentScope instanceof JsonSuperScope) {
			doInWalkers(new DoInWalker() {
				@Override
				public void walk(QueryExecutorJsonWalker walker) {
					walker.invokeJsonDataQuery("*", ctx.getText());
				}
			});
		} else {
			currentScope = new ArrayScope(currentScope, "");
		}
	}

	@Override
	public void exitArrayPart(final ArrayPartContext ctx) {
		log.debug("{} and currentPath:{}", "exitArrayPart", currentScope.getScopeName());
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.doQueryInvoke();
			}
		});
		currentScope = currentScope.getEnclosingScope();
		this.config();
	}

	@Override
	public void enterObjPair(ObjPairContext ctx) {
	}

	@Override
	public void exitObjPair(ObjPairContext ctx) {
	}

	@Override
	public void enterArrayValues(ArrayValuesContext ctx) {
	}

	@Override
	public void exitArrayValues(ArrayValuesContext ctx) {
	}

	@Override
	public void enterKeyValue(final KeyValueContext ctx) {
		log.debug("{} and currentPath:{}", "enterKeyValue", currentScope.getScopeName());
		currentScope = new ObjPairKeyValueScope(currentScope, StrUtils.removeCommon(ctx.STRING().getText()));
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataQuery(currentScope.getScopeName() + ".*", ctx.getText(), true);
			}
		});
	}

	@Override
	public void exitKeyValue(KeyValueContext ctx) {
		log.debug("{} and currentPath:{}", "exitKeyValue", currentScope.getScopeName());
		currentScope = currentScope.getEnclosingScope();
	}

	@Override
	public void exitStringValue(final StringValueContext ctx) {
		log.debug("{} and currentPath:{}", "exitStringValue", currentScope.getScopeName());
		final String text = ctx.STRING().getText();
		// if (currentScope instanceof ObjPairKeyValueScope) {
		// doInWalkers(new DoInWalker() {
		// @Override
		// public void walk(QueryExecutorJsonWalker walker) {
		// walker.invokeOrderBy(currentScope.getScopeName(),
		// StrUtils.removeCommon(text));
		// }
		// });
		// }
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataQuery(currentScope.getScopeName(), StrUtils.removeCommon(text));
			}
		});
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataCondition(currentScope.getScopeName(), StrUtils.removeCommon(text));
			}
		});
	}

	@Override
	public void exitNumberValue(final NumberValueContext ctx) {
		log.debug("{} and currentPath:{}", "exitNumberValue", currentScope.getScopeName());
		TerminalNode number = ctx.NUMBER();
		String text = number.getText();
		BigDecimal numberBig = null;
		final AtomicReference<Object> numValue = new AtomicReference<Object>(null);
		numberBig = new BigDecimal(text);
		log.debug("valveText:{}", text);
		if (text.contains(".")) {
			numValue.set(numberBig.doubleValue());
		} else {
			numValue.set(numberBig.intValue());
		}
		// if (currentScope instanceof ObjPairKeyValueScope) {
		// doInWalkers(new DoInWalker() {
		// @Override
		// public void walk(QueryExecutorJsonWalker walker) {
		// walker.invokeOrderBy(currentScope.getScopeName(), numValue.get());
		// }
		// });
		// }
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataQuery(currentScope.getScopeName(), numValue.get());
			}
		});

		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataCondition(currentScope.getScopeName(), numValue.get());
			}
		});
	}

	@Override
	public void exitTrueValue(final TrueValueContext ctx) {
		log.debug("{} and currentPath:{}", "exitTrueValue", currentScope.getScopeName());
		// if (currentScope instanceof ObjPairKeyValueScope) {
		// doInWalkers(new DoInWalker() {
		// @Override
		// public void walk(QueryExecutorJsonWalker walker) {
		// walker.invokeOrderBy(currentScope.getScopeName(), true);
		// }
		// });
		// }
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataQuery(currentScope.getScopeName(), true);
			}
		});
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataCondition(currentScope.getScopeName(), true);
			}
		});
	}

	@Override
	public void exitFalseValue(final FalseValueContext ctx) {
		log.debug("{} and currentPath:{}", "exitFalseValue", currentScope.getScopeName());
		// if (currentScope instanceof ObjPairKeyValueScope) {
		// doInWalkers(new DoInWalker() {
		// @Override
		// public void walk(QueryExecutorJsonWalker walker) {
		// walker.invokeOrderBy(currentScope.getScopeName(), false);
		// }
		// });
		// }
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataQuery(currentScope.getScopeName(), false);
			}
		});
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataCondition(currentScope.getScopeName(), false);
			}
		});
	}

	@Override
	public void exitNullValue(final NullValueContext ctx) {
		log.debug("{} and currentPath:{}", "exitNullValue", currentScope.getScopeName());
		// if (currentScope instanceof ObjPairKeyValueScope) {
		// doInWalkers(new DoInWalker() {
		// @Override
		// public void walk(QueryExecutorJsonWalker walker) {
		// walker.invokeOrderBy(currentScope.getScopeName(), null);
		// }
		// });
		// }
		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataQuery(currentScope.getScopeName(), null);
			}
		});

		doInWalkers(new DoInWalker() {
			@Override
			public void walk(QueryExecutorJsonWalker walker) {
				walker.invokeJsonDataCondition(currentScope.getScopeName(), null);
			}
		});
	}

}
