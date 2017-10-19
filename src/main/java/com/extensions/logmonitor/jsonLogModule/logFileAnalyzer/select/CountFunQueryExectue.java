package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import com.extensions.logmonitor.util.BloomFilter;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月7日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Slf4j
public class CountFunQueryExectue extends BaseQueryExecute<Integer> {

	private boolean distinct;

	private final String BLOOMFILTERKEY = "BLOOMFILTERKEY";
	private final String COUNTVALUEKEY = "COUNTVALUEKEY";

	@Override
	public void execute(Object value, Long groupId) {
		if (distinct) {
			BloomFilter bloomFilter = super.takeResource(BLOOMFILTERKEY, new BloomFilter(), groupId);
			if (bloomFilter.contains(value.toString())) {
				return;
			}
		}
		Integer takeResource = super.takeResource(COUNTVALUEKEY, 0, groupId);
		log.debug("groupId:{} and age:{} ", groupId, takeResource);
		super.putResource(COUNTVALUEKEY, takeResource + 1, groupId);
	}

	@Override
	public Integer end(Long groupId) {
		return super.takeResource(COUNTVALUEKEY, null, groupId);
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

}
