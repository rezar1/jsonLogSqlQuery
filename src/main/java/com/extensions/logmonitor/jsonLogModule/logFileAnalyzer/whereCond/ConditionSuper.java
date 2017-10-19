package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond;

import java.util.List;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.Scope;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public interface ConditionSuper extends Scope {

	public void visitQuickOptExecute(Map<String, List<OptExecute>> optExecute);

	public boolean visitQuickCondition(Map<OptExecute, Boolean> optCheckResult);

}
