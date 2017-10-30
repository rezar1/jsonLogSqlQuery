package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;

import com.extensions.logmonitor.jsonContentParseies.jsonContentAnalyzer.NeedParsePathMatcher;
import com.extensions.logmonitor.jsonLogModule.queryExecute.Clearable;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月5日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class WhereCondition implements Clearable {
	private MultiCondition conditionSuper;
	private Map<String, List<OptExecute>> optExecuteQuickVisitCache = new HashMap<>();
	private Map<NeedParsePathMatcher, List<OptExecute>> optExecuteQuickVisitCache2 = new HashMap<>();

	private Map<NeedParsePathMatcher, List<OptExecute>> unmodifiableMap;

	private Semaphore semaphore = new Semaphore(1);

	public WhereCondition setCondition(MultiCondition conditionSuper) {
		this.conditionSuper = conditionSuper;
		return this;
	}

	public WhereCondition quickVisitOptExecute() {
		try {
			this.semaphore.acquire();
			this.optExecuteQuickVisitCache.clear();
			this.optExecuteQuickVisitCache2.clear();
			this.conditionSuper.visitQuickOptExecute(optExecuteQuickVisitCache);
			for (Map.Entry<String, List<OptExecute>> entry : this.optExecuteQuickVisitCache.entrySet()) {
				this.optExecuteQuickVisitCache2.put(new NeedParsePathMatcher(entry.getKey()), entry.getValue());
			}
			unmodifiableMap = Collections.unmodifiableMap(this.optExecuteQuickVisitCache2);
		} catch (Exception e) {
			log.error("can not acquire semaphore:{} ", e);
		} finally {
			this.semaphore.release();
		}
		return this;
	}

	/**
	 * @param antrlParseFieldPaths
	 */
	public void fillParseFieldPaths(Set<String> antrlParseFieldPaths) {
		for (String key : this.optExecuteQuickVisitCache.keySet()) {
			key = key.replaceAll(OptExecute.COLUMN_NAME_PREFIX, "");
			key = key.replaceAll(OptExecute.FUN_CALL_PREFIX, "");
			antrlParseFieldPaths.add(key);
		}
	}

	public boolean checkWhereIsSuccess(Map<OptExecute, Boolean> executeResult) {
		log.debug("executeWhereResult:{}", executeResult);
		return this.conditionSuper.visitQuickCondition(executeResult);
	}

	public Map<String, List<OptExecute>> getOptExecuteQuickVisitCache() {
		return Collections.unmodifiableMap(this.optExecuteQuickVisitCache);
	}

	public Map<NeedParsePathMatcher, List<OptExecute>> getOptExecuteQuickVisitCache2() {
		return unmodifiableMap;
	}

	@SuppressWarnings("unchecked")
	public List<OptExecute> findOptExecutes(String path) {
		for (Map.Entry<NeedParsePathMatcher, List<OptExecute>> entry : this.optExecuteQuickVisitCache2.entrySet()) {
			NeedParsePathMatcher key = entry.getKey();
			if (key.fullMatch(OptExecute.COLUMN_NAME_PREFIX + path)) {
				return entry.getValue();
			}
		}
		return Collections.EMPTY_LIST;
	}

	@Override
	public void clearResource() {
	}

}
