package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;

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
@EqualsAndHashCode(callSuper = true)
public class WhereScope extends BaseScope {

	private WhereCondition whereCondition;

	public WhereScope(Scope enclosingScope, WhereCondition whereCondition) {
		super(enclosingScope);
		this.whereCondition = whereCondition;
	}

	@Override
	public String getScopeName() {
		return "WhereScope";
	}

}
