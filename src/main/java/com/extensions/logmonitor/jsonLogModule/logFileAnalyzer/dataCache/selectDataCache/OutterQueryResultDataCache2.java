package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.selectDataCache;

import java.util.List;
import java.util.Map;

import org.javastack.kvstore.KVStoreFactory;
import org.javastack.kvstore.holders.LongHolder;
import org.javastack.kvstore.structures.btree.BplusTree;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.orderByDataCache.SingleOrderByDataCache;

/****
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月17日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class OutterQueryResultDataCache2 {

	private String btreeFile = "/Users/rezar/Desktop/test";

	private BplusTree<LongHolder, QueryResultDataItem2> createTree;

	/**
	*
	*/
	public OutterQueryResultDataCache2() {
		KVStoreFactory<LongHolder, QueryResultDataItem2> factory = new KVStoreFactory<LongHolder, QueryResultDataItem2>(
				LongHolder.class, QueryResultDataItem2.class);
		try {
			createTree = factory.createTree(factory.createTreeOptionsDefault().set(KVStoreFactory.FILENAME, btreeFile));
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		OutterQueryResultDataCache2 dataCache = new OutterQueryResultDataCache2();
		QueryResultDataItem2 cacheData = new QueryResultDataItem2(123l, 3);
		cacheData.setFileName("Rezar.log");
		dataCache.cacheRecord(cacheData);
	}

	public void cacheRecord(QueryResultDataItem2 cacheData) {
		createTree.put(LongHolder.valueOf(cacheData.getRecordId()), cacheData);
	}

	public List<QueryResultDataItem2> getCacheRecord(Integer startOffset, Integer batchSize) {
		return null;
	}

	public void setOrderByDataCache(SingleOrderByDataCache orderByDataCache) {

	}

	public void setGroupHavingFilter(Map<Long, Boolean> havingFilter) {

	}

}
