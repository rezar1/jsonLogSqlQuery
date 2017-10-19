
package com.extensions.logmonitor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.whenNew;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.appdynamics.extensions.yml.YmlReader;
import com.extensions.logmonitor.LogMetrics;
import com.extensions.logmonitor.LogMonitor;
import com.extensions.logmonitor.LogMonitorTask;
import com.extensions.logmonitor.config.Log;
import com.extensions.logmonitor.processors.FilePointerProcessor;
import com.extensions.logmonitor.util.LogMonitorUtil;
import com.google.common.collect.Maps;
import com.singularity.ee.agent.systemagent.api.AManagedMonitor;
import com.singularity.ee.agent.systemagent.api.MetricWriter;
import com.singularity.ee.agent.systemagent.api.exception.TaskExecutionException;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ LogMonitor.class, LogMonitorUtil.class, YmlReader.class })
@PowerMockIgnore({ "org.apache.*, javax.xml.*" })
public class LogMonitorTest {

	@Mock
	private MetricWriter mockMetricWriter;
	@Mock
	private LogMonitorTask mockLogMonitorTask;
	@Mock
	private FilePointerProcessor mockFilePointerProcessor;
	private LogMonitor classUnderTest;

	@Before
	public void init() throws Exception {
		whenNew(MetricWriter.class).withArguments(any(AManagedMonitor.class), anyString()).thenReturn(mockMetricWriter);
		whenNew(FilePointerProcessor.class).withNoArguments().thenReturn(mockFilePointerProcessor);
		setupTestMetricsAndLogMetricTask();
		classUnderTest = spy(new LogMonitor());
	}

	@Test(expected = TaskExecutionException.class)
	public void testWithNullArgsShouldResultInException() throws Exception {
		classUnderTest.execute(null, null);
	}

	@Test(expected = TaskExecutionException.class)
	public void testWithEmptyArgsShouldResultInException() throws Exception {
		classUnderTest.execute(new HashMap<String, String>(), null);
	}

	@Test(expected = TaskExecutionException.class)
	public void testWithNoValidLogConfigResultInException() throws Exception {
		Map<String, String> args = Maps.newHashMap();
		args.put("config-file", "src/test/resources/conf/no-valid-log-config.yaml");

		classUnderTest.execute(args, null);
	}

	@Test
	public void testExceptionOccurredInLogMonitorTask() throws Exception {
		Map<String, String> args = Maps.newHashMap();
		args.put("config-file", "src/test/resources/conf/single-log-config.yaml");

		when(mockLogMonitorTask.call()).thenThrow(new FileNotFoundException());

		classUnderTest.execute(args, null);

		verifyNotCalled("Custom Metrics|LogMonitor|TestLog|Search String|Debug", BigInteger.valueOf(7));
		verifyNotCalled("Custom Metrics|LogMonitor|TestLog|Search String|Info", BigInteger.valueOf(8));
		verifyNotCalled("Custom Metrics|LogMonitor|TestLog|Search String|Error", BigInteger.valueOf(9));
		verifyNotCalled("Custom Metrics|LogMonitor|TestLog|File size (Bytes)", BigInteger.valueOf(10));
	}

	@Test
	public void testMetricsAreReportedCorrectly() throws Exception {
		Map<String, String> args = Maps.newHashMap();
		args.put("config-file", "src/test/resources/conf/single-log-config.yaml");

		classUnderTest.execute(args, null);

		verifyMetric("Custom Metrics|LogMonitor|TestLog|Search String|Debug", BigInteger.valueOf(8));
		verifyMetric("Custom Metrics|LogMonitor|TestLog|Search String|Info", BigInteger.valueOf(8));
		verifyMetric("Custom Metrics|LogMonitor|TestLog|Search String|Error", BigInteger.valueOf(9));
		verifyMetric("Custom Metrics|LogMonitor|TestLog|File size (Bytes)", BigInteger.valueOf(10));
	}

	private void setupTestMetricsAndLogMetricTask() throws Exception {
		LogMetrics logMetrics = new LogMetrics();
		logMetrics.add("TestLog|Search String|Debug", BigInteger.valueOf(8));
		logMetrics.add("TestLog|Search String|Info", BigInteger.valueOf(8));
		logMetrics.add("TestLog|Search String|Error", BigInteger.valueOf(9));
		logMetrics.add("TestLog|File size (Bytes)", BigInteger.valueOf(10));

		whenNew(LogMonitorTask.class).withArguments(any(FilePointerProcessor.class), any(Log.class), any(Map.class))
				.thenReturn(mockLogMonitorTask);

		when(mockLogMonitorTask.call()).thenReturn(logMetrics);
	}

	private void verifyMetric(String metricName, BigInteger value) throws Exception {
		verifyPrivate(classUnderTest).invoke("printCollectiveObservedCurrent", metricName, value);
	}

	private void verifyNotCalled(String metricName, BigInteger value) throws Exception {
		verifyPrivate(classUnderTest, never()).invoke("printCollectiveObservedCurrent", metricName, value);
	}

}
