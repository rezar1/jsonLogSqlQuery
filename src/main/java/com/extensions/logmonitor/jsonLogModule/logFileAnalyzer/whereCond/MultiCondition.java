package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.BaseScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.Scope;

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
public class MultiCondition extends BaseScope implements ConditionSuper {

	public MultiCondition(Scope enclosingScope) {
		super(enclosingScope);
	}

	private List<ConditionSuper> conditions = new ArrayList<>();

	public MultiCondition addCondition(ConditionSuper conditionSuper) {
		this.conditions.add(conditionSuper);
		return this;
	}

	public MultiCondition addAllCondition(List<ConditionSuper> conditionSuperes) {
		this.conditions.addAll(conditionSuperes);
		return this;
	}

	public List<ConditionSuper> getConditions() {
		return conditions;
	}

	@Override
	public String toString() {
		return "'MultiCondition': [" + conditions + "]";
	}

	@Override
	public void visitQuickOptExecute(Map<String, List<OptExecute>> optExecute) {
		for (ConditionSuper cs : this.conditions) {
			if (cs instanceof SingleCondition || cs instanceof MultiCondition) {
				cs.visitQuickOptExecute(optExecute);
			}
		}
	}

	public boolean visitQuickCondition(Map<OptExecute, Boolean> optCheckResult) {
		Boolean checkResult = null;
		if (this.conditions.size() == 1) {
			checkResult = this.conditions.get(0).visitQuickCondition(optCheckResult);
		} else {
			Boolean preResult = null;
			Boolean currentResult = null;
			ConditionSuper preOptType = null;
			for (ConditionSuper cs : this.conditions) {
				if (cs instanceof MultiCondition) {
					currentResult = cs.visitQuickCondition(optCheckResult);
				} else if (cs instanceof SingleCondition) {
					currentResult = cs.visitQuickCondition(optCheckResult);
				} else if (isOpt(cs)) {
					preOptType = cs;
				}
				preResult = check(preResult, currentResult, preOptType);
			}
			checkResult = preResult;
		}
		if (checkResult == null) {
			return true;
		}
		return checkResult;
	}

	/**
	 * @param preResult
	 * @param preOptType
	 * @param preOptType2
	 * @return
	 */
	private Boolean check(Boolean preResult, Boolean currentResult, ConditionSuper preOptType) {
		if (preResult == null) {
			return currentResult;
		}
		if (preResult != null && preOptType != null) {
			if (preOptType instanceof OptOrCondition) {
				return preResult || currentResult;
			} else if (preOptType instanceof OptXOrCondition) {
				return preResult ^ currentResult;
			} else if (preOptType instanceof OptAndCondition) {
				return preResult && currentResult;
			}
		}
		return null;
	}

	/**
	 * @param cs
	 * @return
	 */
	private boolean isOpt(ConditionSuper cs) {
		return cs instanceof OptAndCondition || cs instanceof OptOrCondition || cs instanceof OptXOrCondition;
	}

}
