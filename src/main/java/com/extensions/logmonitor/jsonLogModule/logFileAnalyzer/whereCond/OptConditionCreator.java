package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond;

import java.util.List;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.EqOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.GteOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.InOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.LikeOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.LtOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.LteOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.NotEqOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.NotInOpt;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.optExecutes.NotLikeOpt;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class OptConditionCreator {

	public OptExecute createEq(Object originMatchValue) {
		return new EqOpt(originMatchValue);
	}

	public OptExecute createNotEq(Object originMatchValue) {
		return new NotEqOpt(originMatchValue);
	}

	public OptExecute createGt(Object originMatchValue) {
		return new GteOpt(originMatchValue);
	}

	public OptExecute createGte(Object originMatchValue) {
		return new GteOpt(originMatchValue);
	}

	public OptExecute createlt(Object originMatchValue) {
		return new LtOpt(originMatchValue);
	}

	public OptExecute createLte(Object originMatchValue) {
		return new LteOpt(originMatchValue);
	}

	public OptExecute createLike(String originMatchValue) {
		return new LikeOpt(originMatchValue);
	}

	public OptExecute createNotLike(String originMatchValue) {
		return new NotLikeOpt(originMatchValue);
	}

	public OptExecute createIn(List<Object> originMatchValue) {
		return new InOpt(originMatchValue);
	}

	public OptExecute createNotIn(List<Object> originMatchValue) {
		return new NotInOpt(originMatchValue);
	}

}
