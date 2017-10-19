package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.order;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月10日
 * @Desc this guy is to lazy , noting left.
 *
 */
public interface OrderByDataItem {

	void initValueSize(int expectSize);

	void addCacheData(int index, Object orderByCompareValue);

	public void setRecordId(long recordId);

	long getRecordId();

	/**
	 * @return
	 */
	Object[] getOrderByValues();

}