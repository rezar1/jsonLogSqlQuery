package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.TwoTuple;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.data.GroupFunData;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.data.GroupFunData.DoInLockCallback;
import com.extensions.logmonitor.util.LoadCache;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月7日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class SumValueQueryExecute extends BaseQueryExecute<Double> {

	private LoadCache<String, GroupFunData<BigDecimal, Integer>> loadCache = new LoadCache<String, GroupFunData<BigDecimal, Integer>>(
			new LoadCache.InitValue<String, GroupFunData<BigDecimal, Integer>>() {
				@Override
				public GroupFunData<BigDecimal, Integer> initValue(String key) {
					return new GroupFunData<BigDecimal, Integer>(new BigDecimal("0.0"), 0);
				}
			});

	@Override
	public void execute(final Object value, Long groupId) {
		if (StringUtils.isNumeric(value.toString())) {
			final GroupFunData<BigDecimal, Integer> avgDatas = this.loadCache.getCache(getGroupIdStr(groupId));
			avgDatas.safeSetDatas(new DoInLockCallback<BigDecimal, Integer>() {
				@Override
				public void doInLock(BigDecimal data1, Integer data2) {
					data1 = data1.add(new BigDecimal(value.toString()));
					avgDatas.unsafeSetDatas(data1, data2);
				}
			});
		}
	}

	@Override
	public Double end(Long groupId) {
		TwoTuple<BigDecimal, Integer> safeReadDatas = this.loadCache.getCache(getGroupIdStr(groupId)).safeReadDatas();
		return safeReadDatas.first.doubleValue();
	}

}
