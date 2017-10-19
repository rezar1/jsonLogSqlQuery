package com.extensions.logmonitor.jsonLogModule.queryExecute;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import com.extensions.logmonitor.jsonLogModule.configuration.LogAnalyzerConfiguration;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.FileOutterQueryResultDataCache;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataCache;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.QueryResultDataItem;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache.TriggerOutterCacheInvoker;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.group.GroupExecutor;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order.OrderExecutor;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.SelectPart;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.whereCond.WhereCondition;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月4日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
@Slf4j
public class QueryExecutor {

	private SelectPart selectPart;
	private String fromTableLogName;
	private WhereCondition whereCondition;
	private GroupExecutor groupExecutor;
	private OrderExecutor orderExecutor;
	private Long[] limitInfos;

	private QueryResultDataCache dataCache = new FileOutterQueryResultDataCache();// InnterQueryResultDataCache
																					// OutterQueryResultDataCache();
																					// 	FileOutterQueryResultDataCache

	private int querySize;
	private TriggerOutterCacheInvoker triggerOutterCacheInvoker;
	private AtomicLong recordCount = new AtomicLong(0);

	public Long recordIncrement() {
		long recordId = 0;
		if ((recordId = this.recordCount.incrementAndGet()) > LogAnalyzerConfiguration.MAX_SORT_BUFF_SIZE_VALUE
				&& this.triggerOutterCacheInvoker != null) {
			this.triggerOutterCacheInvoker.trigger(this.dataCache);
		}
		return recordId;
	}

	public QueryResultDataItem createQueryResultDataItem() {
		QueryResultDataItem setFileName = new QueryResultDataItem(querySize);
		return setFileName;
	}

	public List<QueryResultDataItem> doHandle() {
		List<QueryResultDataItem> cacheRecord = null;
		try {
			if (this.orderExecutor != null) {
				this.orderExecutor.executeSort();
				this.dataCache.setOrderByDataCache(this.orderExecutor.getSingleOrderByDataCache());
			} else if (this.groupExecutor != null && this.groupExecutor.needHaving()) {
				this.dataCache.setGroupHavingFilter(this.groupExecutor.getHavingFilter());
			}
			if (this.limitInfos != null) {
				cacheRecord = this.dataCache.getCacheRecord(limitInfos[0].intValue(), limitInfos[1].intValue());
			} else {
				cacheRecord = this.dataCache.getCacheRecord(null, null);
			}
			this.selectPart.fillAllFunQuery(cacheRecord);
			this.clearResource();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("error while doHandle:{}", e);
		} finally {
			this.clearResource();
		}
		return cacheRecord;
	}

	public QueryExecutor clearResource() {
		if (this.selectPart instanceof Clearable) {
			((Clearable) this.selectPart).clearResource();
		}
		if (this.whereCondition != null) {
			this.whereCondition.clearResource();
		}
		if (this.orderExecutor != null) {
			this.orderExecutor.clearResource();
		}
		if (this.groupExecutor != null) {
			this.groupExecutor.clearResource();
		}
		return this;
	}

	/**
	 * 
	 */
	public void initGroupByExecutor() {
		this.groupExecutor = new GroupExecutor();
	}

}
