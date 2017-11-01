package com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonScope;

import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute.CheckPathInterface;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.scopes.Scope;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月9日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ObjPairKeyValueScope extends ObjectScope implements CheckPathInterface {

	private boolean needDoHandle;
	private boolean needDoParseMore;

	public ObjPairKeyValueScope(Scope enclosingScope, String currentObjName) {
		super(enclosingScope, currentObjName);
	}

	@Override
	public boolean needDoHandle() {
		return this.needDoHandle;
	}

	@Override
	public boolean needDoParseMore() {
		return this.needDoParseMore;
	}

}
