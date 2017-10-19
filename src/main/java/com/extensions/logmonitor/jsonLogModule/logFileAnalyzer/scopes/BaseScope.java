package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年7月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
public abstract class BaseScope implements Scope {
	Scope enclosingScope; // null if global (outermost) scope

	public BaseScope(Scope enclosingScope) {
		this.enclosingScope = enclosingScope;
	}

	@Override
	public Scope getEnclosingScope() {
		return this.enclosingScope;
	}

	@Override
	public String getScopeName() {
		return this.getClass().getSimpleName();
	}

}
