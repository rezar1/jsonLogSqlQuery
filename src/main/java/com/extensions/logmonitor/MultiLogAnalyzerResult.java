package com.extensions.logmonitor;

import java.math.BigInteger;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class MultiLogAnalyzerResult { 

	private Map<String, BigInteger> metrics = new ConcurrentHashMap<String, BigInteger>();

	public void add(String metricName) {
		BigInteger value = metrics.get(metricName);
		if (value != null) {
			value = value.add(BigInteger.ONE);
		} else {
			value = BigInteger.ONE;
		}
		add(metricName, value);
	}

	public void add(String metricName, BigInteger value) {
		this.metrics.put(metricName, value);
	}

	public void addAll(Map<String, BigInteger> metrics) {
		this.metrics.putAll(metrics);
	}

	public Map<String, BigInteger> getMetrics() {
		return this.metrics;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
