package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond;

import java.util.List;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.BaseScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.Scope;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class OptAndCondition extends BaseScope implements ConditionSuper {

	public OptAndCondition(Scope enclosingScope) {
		super(enclosingScope);
	}

	@Override
	public String toString() {
		return " AND ";
	}

	@Override
	public void visitQuickOptExecute(Map<String, List<OptExecute>> optExecute) {

	}

	@Override
	public boolean visitQuickCondition(Map<OptExecute, Boolean> optCheckResult) {
		return false;
	}

}
