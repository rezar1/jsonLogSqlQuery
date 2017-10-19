package com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.BaseScope;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.Scope;
import com.extensions.logmonitor.util.GenericsUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class ArrayScope extends BaseScope {

	private String currentPath;

	public ArrayScope(Scope enclosingScope, String currentArrayName) {
		super(enclosingScope);
		if (GenericsUtils.isNullOrEmpty(enclosingScope.getScopeName())) {
			this.currentPath = currentArrayName;
		} else {
			this.currentPath = enclosingScope.getScopeName() + "." + currentArrayName;
		}
	}

	@Override
	public String getScopeName() {
		return currentPath;
	}

}
