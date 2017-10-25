package com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.jsonParserExecute;

import java.util.ArrayList;
import java.util.List;

import com.extensions.logmonitor.util.TupleUtil;
import com.extensions.logmonitor.util.TwoTuple;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月24日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class TestPathMatcher {

	private List<String> antrlParseFieldPaths = new ArrayList<>();

	public TwoTuple<Boolean, Boolean> checkNeedDoParse(String currentParsePath, boolean isSuper) {
		boolean currentNeedParse = false;
		boolean needParseChild = false;
		for (String needParsePath : this.antrlParseFieldPaths) {
			if (needParsePath.startsWith(currentParsePath)) {
				if (!currentNeedParse) {
					boolean isPathMatch = needParsePath.equals(currentParsePath)
							|| ((isSuper) && needParsePath.equals(currentParsePath + ".*"));
					if (isPathMatch) {
						currentNeedParse = true;
					}
				}
				if (!needParseChild) {
					if (needParsePath.length() > currentParsePath.length()) {
						needParseChild = true;
					}
				}
			}
			if (currentNeedParse && needParseChild) {
				break;
			}
		}
		if (!needParseChild) {
			if (currentParsePath.equals("*") && this.antrlParseFieldPaths.size() > 1) {
				needParseChild = true;
			}
		}
		return TupleUtil.tuple(currentNeedParse, needParseChild);
	}

	public static void main(String[] args) {
		TestPathMatcher tpm = new TestPathMatcher();
		List<String> antrlParseFieldPaths2 = tpm.antrlParseFieldPaths;
		antrlParseFieldPaths2.add("app.info.*");
		antrlParseFieldPaths2.add("app.*");
		antrlParseFieldPaths2.add("app.info.id");
		// antrlParseFieldPaths2.add("*");

		System.out.println(tpm.checkNeedDoParse("*", true));
		System.out.println(tpm.checkNeedDoParse("app", true));
		System.out.println(tpm.checkNeedDoParse("app.info", true));
		System.out.println(tpm.checkNeedDoParse("app.info.id", false));
	}

}
