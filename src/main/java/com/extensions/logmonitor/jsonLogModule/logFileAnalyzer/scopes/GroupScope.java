package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class GroupScope extends BaseScope {

	private WhereCondition whereCondition;

	public GroupScope(Scope enclosingScope, WhereCondition whereCondition) {
		super(enclosingScope);
		this.whereCondition = whereCondition;
	}

	public WhereCondition getWhereCondition() {
		return this.whereCondition;
	}

}
