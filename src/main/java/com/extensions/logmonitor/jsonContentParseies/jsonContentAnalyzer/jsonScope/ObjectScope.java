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
public class ObjectScope extends BaseScope {

	private String currentPath;

	public ObjectScope(Scope enclosingScope, String currentObjName) {
		super(enclosingScope);
		if (GenericsUtils.isNullOrEmpty(enclosingScope.getScopeName())) {
			this.currentPath = currentObjName;
		} else {
			this.currentPath = enclosingScope.getScopeName() + "." + currentObjName;
		}
	}

	@Override
	public String getScopeName() {
		return currentPath;
	}

}
