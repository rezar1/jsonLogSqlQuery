package com.extensions.logmonitor.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser.JsonLogDataQueryHandler;
import com.extensions.logmonitor.jsonLogModule.queryExecute.QueryExecutor;
import com.extensions.logmonitor.util.GenericsUtils;
import com.extensions.logmonitor.util.LoadCache.InitValue;

import lombok.Data;

/**
 * 
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
public class LogJsonAnalyzer {

	private String displayName;
	private String logDirectory;
	private String logName;

	public LogJsonAnalyzer(String displayName, String logDirectory, String logName) {
		this.displayName = displayName;
		this.logDirectory = logDirectory;
		this.logName = logName;
	}

	InitValue<String, List<SearchInfo>> initValue = new InitValue<String, List<SearchInfo>>() {
		@Override
		public List<SearchInfo> initValue(String key) {
			return new ArrayList<>();
		}
	};
	private Map<String, List<SearchInfo>> searchInfoMaps = new HashMap<String, List<SearchInfo>>();

	private Map<String, JsonLogDataQueryHandler> jsonLogDataQueryHandlerMap = new HashMap<>();

	/**
	 * @param logEventTypeStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SearchInfo> findSearchInfos(String logEventTypeStr) {
		List<SearchInfo> list = searchInfoMaps.get(logEventTypeStr);
		if (list == null) {
			return Collections.EMPTY_LIST;
		}
		return list;
	}

	public JsonLogDataQueryHandler findJsonLogDataQueryHandler(String logEventTypeStr) {
		JsonLogDataQueryHandler jsonLogDataQueryHandler = jsonLogDataQueryHandlerMap.get(logEventTypeStr);
		if (jsonLogDataQueryHandler == null) {
			List<SearchInfo> findSearchInfos = this.findSearchInfos(logEventTypeStr);
			if (GenericsUtils.isNullOrEmpty(findSearchInfos)) {
				return null;
			}
			List<QueryExecutor> queryExecutors = new ArrayList<>();
			for (SearchInfo si : findSearchInfos) {
				queryExecutors.add(si.getQueryExecutor());
			}
			jsonLogDataQueryHandler = new JsonLogDataQueryHandler(queryExecutors);
			this.jsonLogDataQueryHandlerMap.put(logEventTypeStr, jsonLogDataQueryHandler);
		}
		return jsonLogDataQueryHandler;
	}

	public LogJsonAnalyzer addSearchInfo(SearchInfo searchInfo) {
		List<SearchInfo> cache = searchInfoMaps.get(searchInfo.getLogType());
		System.out.println("cache is:" + cache);
		if (cache == null) {
			cache = new ArrayList<>();
			this.searchInfoMaps.put(searchInfo.getLogType(), cache);
		}
		cache.add(searchInfo);
		return this;
	}

	public Set<String> getAllHandleLogEventTypes() {
		return this.jsonLogDataQueryHandlerMap.keySet();
	}

	public void analyzerQuery() {
	}

}
