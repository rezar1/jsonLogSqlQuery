package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class SelectSuperScope extends BaseScope {

	/**
	 * @param enclosingScope
	 */
	public SelectSuperScope() {
		super(null);
	}

	@Override
	public String getScopeName() {
		return this.getClass().getSimpleName();
	}

}
