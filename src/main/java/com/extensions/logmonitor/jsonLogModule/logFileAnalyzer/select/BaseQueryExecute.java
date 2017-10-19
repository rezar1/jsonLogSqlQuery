package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import java.util.HashMap;
import java.util.Map;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataCache;
import com.extensions.logmonitor.util.GenericsUtils;

import lombok.Data;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月7日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
public abstract class BaseQueryExecute<T> implements QueryExecute<T> {

	private QueryResultDataCache queryResultDtaCache;
	protected String GROUPIDKEY = "GROUP_ID_KEY&&&&***&&";

	/**
	 * 非静态的类型,导致多个对象具有多个本地线程变量
	 */
	private ThreadLocal<Map<String, Map<String, Object>>> threadResource = new ThreadLocal<Map<String, Map<String, Object>>>() {
		@Override
		protected Map<String, Map<String, Object>> initialValue() {
			return new HashMap<>();
		}
	};

	private String querySuperPath;
	private String fieldName;
	private String alias;

	public void setQueryResultDataCache(QueryResultDataCache queryResultDtaCache) {
		this.queryResultDtaCache = queryResultDtaCache;
	}

	@Override
	public void setQueryPath(String queryPath) {
		int index = -1;
		if ((index = queryPath.lastIndexOf(".")) != -1) {
			this.querySuperPath = queryPath.substring(0, index);
			this.fieldName = queryPath.substring(index + 1);
		} else {
			this.fieldName = queryPath;
			this.querySuperPath = "";
		}
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getShowName() {
		if (GenericsUtils.notNullAndEmpty(this.alias)) {
			return this.alias;
		} else {
			return this.getQueryPathWithFieldName();
		}
	}

	@Override
	public String getQueryPathWithFieldName() {
		return (GenericsUtils.notNullAndEmpty(this.querySuperPath) ? this.querySuperPath + "." : "") + this.fieldName;
	}

	protected BaseQueryExecute<T> putResource(String resourceKey, Object value, Long groupId) {
		getResourceMap(groupId).put(resourceKey, value);
		return this;
	}

	private Map<String, Object> getResourceMap(Long groupId) {
		Map<String, Map<String, Object>> resourceMap = this.threadResource.get();
		Map<String, Object> map = resourceMap.get(getGroupIdStr(groupId));
		if (map == null) {
			map = new HashMap<>();
			resourceMap.put(getGroupIdStr(groupId), map);
		}
		return map;
	}

	/**
	 * @param groupId
	 * @return
	 */
	private String getGroupIdStr(Long groupId) {
		String groupStr = GROUPIDKEY; // by default
		if (groupId != null) {
			groupStr = String.valueOf(groupId);
		}
		return groupStr;
	}

	@SuppressWarnings("unchecked")
	protected <D> D takeResource(String resourceKey, D defaultValue, Long groupId) {
		Object object = getResourceMap(groupId).get(resourceKey);
		if (object == null && defaultValue != null) {
			this.putResource(resourceKey, defaultValue, groupId);
		}
		return (D) getResourceMap(groupId).get(resourceKey);
	}

	public String toString() {
		return this.getClass().getSimpleName();
	}

	public void clearResource() {
		this.threadResource.remove();
	}

	public void clearResource(Long groupId) {
		this.threadResource.get().remove(this.getGroupIdStr(groupId));
	}

}
