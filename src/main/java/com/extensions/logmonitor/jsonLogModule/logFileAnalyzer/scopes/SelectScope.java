package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.SelectPart;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class SelectScope extends BaseScope {

	private SelectPart selectPart;

	public SelectScope(Scope enclosingScope, SelectPart selectPart) {
		super(enclosingScope);
		this.selectPart = selectPart;
	}

	@Override
	public String getScopeName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * @return the selectPart
	 */
	public SelectPart getSelectPart() {
		return selectPart;
	}

}
