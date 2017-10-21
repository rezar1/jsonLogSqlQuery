package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.data.GroupFunData;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.data.GroupFunData.DoInLockCallback;
import com.extensions.logmonitor.util.LoadCache;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月8日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class MinFunQueryExecute extends BaseQueryExecute<Object> {

	private LoadCache<Long, GroupFunData<Object, Integer>> loadCache2 = new LoadCache<Long, GroupFunData<Object, Integer>>(
			new LoadCache.InitValue<Long, GroupFunData<Object, Integer>>() {
				@Override
				public GroupFunData<Object, Integer> initValue(Long key) {
					return new GroupFunData<Object, Integer>(null, 0);
				}
			});

	@Override
	public void execute(final Object value, Long groupId) {
		final GroupFunData<Object, Integer> cache = loadCache2.getCache(check(groupId));
		cache.safeSetDatas(new DoInLockCallback<Object, Integer>() {
			@Override
			public void doInLock(Object data1, Integer data2) {
				if (StringUtils.isNumeric(value.toString())) {
					BigDecimal currentValue = new BigDecimal(value.toString());
					BigDecimal preValue = (BigDecimal) data1;
					if (preValue == null || (currentValue.compareTo(preValue)) < 0) {
						cache.unsafeSetDatas(currentValue, null);
					}
				} else if (value instanceof String) {
					String currentValue = value.toString();
					String preValue = (String) data1;
					if (preValue == null || currentValue.compareTo(preValue) < 0) {
						cache.unsafeSetDatas(currentValue, null);
					}
				}
			}
		});
	}

	@Override
	public Object end(Long groupId) {
		return loadCache2.getCache(groupId).safeReadDatas().first.toString();
	}

}
