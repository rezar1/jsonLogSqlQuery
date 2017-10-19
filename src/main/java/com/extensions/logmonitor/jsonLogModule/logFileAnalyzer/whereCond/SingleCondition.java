package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond;

import java.util.List;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.BaseScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.Scope;
import com.extensions.logmonitor.util.GenericsUtils;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SingleCondition extends BaseScope implements ConditionSuper {

	/**
	 * @param enclosingScope
	 */
	public SingleCondition(Scope enclosingScope) {
		super(enclosingScope);
	}

	public SingleCondition(Scope enclosingScope, OptExecute optExecute) {
		super(enclosingScope);
		this.optExecute = optExecute;
	}

	private OptExecute optExecute;

	public boolean checkConditionIsTrue(Object value) {
		return optExecute.OptSuccess(value);
	}

	@Override
	public String toString() {
		return optExecute.toString();
	}

	@Override
	public void visitQuickOptExecute(Map<String, List<OptExecute>> optExecuteMap) {
		GenericsUtils.addListIfNotExists(optExecuteMap, this.optExecute.getMatchPath(), this.optExecute);
	}

	@Override
	public boolean visitQuickCondition(Map<OptExecute, Boolean> optCheckResult) {
		return optCheckResult.get(this.getOptExecute());
	}

}
