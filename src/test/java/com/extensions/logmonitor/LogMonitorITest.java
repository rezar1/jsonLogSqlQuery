package com.extensions.logmonitor;

import java.util.Map;

import org.junit.Test;

import com.extensions.logmonitor.LogMonitor;
import com.google.common.collect.Maps;
import com.singularity.ee.agent.systemagent.api.TaskOutput;

public class LogMonitorITest {

	private LogMonitor classUnderTest = new LogMonitor();

	@Test
	public void testMetricsCollection() throws Exception {
		Map<String, String> args = Maps.newHashMap();
		args.put("config-file", "src/test/resources/conf/config.yaml");
		TaskOutput result = classUnderTest.execute(args, null);
		System.out.println("result : " + result.getStatusMessage());
		// assertTrue(result.getStatusMessage().contains("successfully
		// completed"));
	}

}
