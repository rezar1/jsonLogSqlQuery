package com.extensions.logmonitor;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.extensions.logmonitor.config.LogJsonAnalyzer;
import com.extensions.logmonitor.config.SearchInfo;
import com.extensions.logmonitor.logFileAnalyzer.LogMonitorTaskForJsonAnalyzer;
import com.extensions.logmonitor.processors.FilePointer;
import com.extensions.logmonitor.processors.FilePointerProcessor;

@RunWith(MockitoJUnitRunner.class)
public class LogMonitorTaskTest {

	public LogMonitorTask classUnderTest;

	@Mock
	private FilePointerProcessor mockFilePointerProcessor;

	public static String btreeFile = "/Users/rezar/Desktop/12";

	public static void main(String[] args) {
		String Str = "\"LogEventType\":\"Sa_Req\",\"request\"";
		int indexOf = Str.indexOf("LogEventType");
		System.out.println("logEventTypeStr indexOf:" + indexOf);
		if (indexOf == -1) {
			return;
		}
		String logEventTypeStr = Str.substring(indexOf + 15, Str.indexOf("\"", indexOf + 17));
		System.out.println(logEventTypeStr);
	}

	@Test
	public void testJsonArrayAnalyzer() throws Exception {
		String logDirectory = "/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles";
		String logName = "test.log";
		FilePointer filePointer = new FilePointer();
		filePointer.setFilename(logDirectory + logName);
		when(mockFilePointerProcessor.getFilePointer(anyString(), anyString())).thenReturn(filePointer);
		LogJsonAnalyzer logJsonAnalyzer = new LogJsonAnalyzer("TestLog",
				"/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles", "test.log");
		SearchInfo searchInfo = new SearchInfo("select * from testJson where subUserInfos[*].adslotId in (81804026,63016870,67589938)");
		logJsonAnalyzer.addSearchInfo(searchInfo);
		LogMonitorTaskForJsonAnalyzer analyzer = new LogMonitorTaskForJsonAnalyzer(mockFilePointerProcessor,
				logJsonAnalyzer);
		analyzer.call();
	}

	@Test
	public void testJsonAnalyzer() throws Exception {
		String logDirectory = "/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles";
		String logName = "test.log";
		FilePointer filePointer = new FilePointer();
		filePointer.setFilename(logDirectory + logName);
		when(mockFilePointerProcessor.getFilePointer(anyString(), anyString())).thenReturn(filePointer);
		LogJsonAnalyzer logJsonAnalyzer = new LogJsonAnalyzer("TestLog",
				"/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles", "test.log");
		// SearchInfo searchInfo = new SearchInfo(
		// "select name,age,sum(age) as ageSum ,count(age) as ageCount from
		// testJson where age between 50 and 95 group by age");
		SearchInfo searchInfo = new SearchInfo(
				"select distinct regex_group('(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:?)+ \\d{3}',1,time) as timeOfDay,age from testJson where age between 50 and 95 order by day('yyyy-MM-dd HH:mm:ss SSS',time) desc , age desc limit 0,100");
		logJsonAnalyzer.addSearchInfo(searchInfo);
		LogMonitorTaskForJsonAnalyzer analyzer = new LogMonitorTaskForJsonAnalyzer(mockFilePointerProcessor,
				logJsonAnalyzer);
		analyzer.call();
		/**
		 * 
		 * 优化记录:
		 * 
		 * 1.10000000:14:28:37:444 --> 14:40:14:232 --->maxHeapSize:xxx
		 * 2.10000000:15:04:26:809 --> 15:16:37:519 --->maxHeapSize:-》900M
		 * (修改了orderByDataItem的数据orderTypes --> comparator)
		 * 3.10000000:18:39:46:006 --> 18:51:40:616 --->maxHeapSize:-》800M
		 * (修改了orderByDataItemWithUnicode作为比较的数据,不再引用原始比较对象,比较对象的unicode大小，PS:这样无效,对于字符串无法通过16进制unicode的和来进行比较)
		 * 4.10000000:18:23:58:611 --> 18:34:08:534 <br/>
		 * 5.10000000:16:37:11:042 --> 16:38:00:210 --->maxHeapSize:150M
		 * (修改了查询结果的存储,直接按顺序存储到结果文件里面) <br/>
		 * 6.10000000: 17:42:25:908 --> 17:50:13:457 435129 ms
		 * --->maxHeapSize:500M --》select name,age from testJson where age
		 * between 50 and 95 order by age desc,这个最优目前
		 * 
		 * 7.10000000： 439816 ms <br/>
		 * select name,age from testJson where age between 50 and 95 order by
		 * age desc <br/>
		 * 减少中间对象的创建(修改了批量antlr文本处理)
		 * 
		 */
	}

	@Test
	public void testJsonGrupAnalyzer() throws Exception {
		String logDirectory = "/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles";
		String logName = "test.log";
		FilePointer filePointer = new FilePointer();
		filePointer.setFilename(logDirectory + logName);
		when(mockFilePointerProcessor.getFilePointer(anyString(), anyString())).thenReturn(filePointer);
		LogJsonAnalyzer logJsonAnalyzer = new LogJsonAnalyzer("TestLog",
				"/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles", "test.log");
		SearchInfo searchInfo = new SearchInfo(
				"select regex_group('(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:?)+ \\d{3}',1,time) as timeOfDay,hour('yyyy-MM-dd HH:mm:ss SSS',time) as hourOfDay,count(age) as ageCount from testJson where age between 50 and 95 group by timeOfDay,hourOfDay order by timeOfDay desc,hourOfDay desc");
		logJsonAnalyzer.addSearchInfo(searchInfo);
		LogMonitorTaskForJsonAnalyzer analyzer = new LogMonitorTaskForJsonAnalyzer(mockFilePointerProcessor,
				logJsonAnalyzer);
		analyzer.call();
		/**
		 * sql:select day('yyyy-MM-dd HH:mm:ss SSS',time) as
		 * dayOfTime,hour('yyyy-MM-dd HH:mm:ss SSS',time) as
		 * hourOfDay,count(age) as ageCount from testJson where age between 50
		 * and 95 group by dayOfTime,hourOfDay
		 * 
		 * 100000000 use time 217969 ms
		 */
	}

	@Test
	public void testLogAna() throws Exception {
		String logDirectory = "/Users/rezar/Desktop/";
		String logName = "test.log";
		FilePointer filePointer = new FilePointer();
		filePointer.setFilename(logDirectory + logName);
		when(mockFilePointerProcessor.getFilePointer(anyString(), anyString())).thenReturn(filePointer);
		LogJsonAnalyzer logJsonAnalyzer = new LogJsonAnalyzer("TestLog", "/Users/rezar/Desktop/", "count.log");
		SearchInfo searchInfo = new SearchInfo(
				"select app_id,sum(appIdCount) as appIdCount from Sa_Req where isSdk=false group by app_id having sum(appIdCount) > 900000");
		logJsonAnalyzer.addSearchInfo(searchInfo);
		LogMonitorTaskForJsonAnalyzer analyzer = new LogMonitorTaskForJsonAnalyzer(mockFilePointerProcessor,
				logJsonAnalyzer);
		analyzer.call();
	}

	// @Test
	// public void testMatchExactStringIsTrue() throws Exception {
	// Log log = new Log();
	// log.setDisplayName("TestLog");
	// log.setLogDirectory("src/test/resources/");
	// log.setLogName("test-log-1.log");
	//
	// SearchString searchString = new SearchString();
	// searchString.setCaseSensitive(false);
	// searchString.setMatchExactString(true);
	// searchString.setPattern("debug");
	// searchString.setDisplayName("Debug");
	//
	// SearchString searchString1 = new SearchString();
	// searchString1.setCaseSensitive(false);
	// searchString1.setMatchExactString(true);
	// searchString1.setPattern("info");
	// searchString1.setDisplayName("Info");
	//
	// SearchString searchString2 = new SearchString();
	// searchString2.setCaseSensitive(false);
	// searchString2.setMatchExactString(true);
	// searchString2.setPattern("error");
	// searchString2.setDisplayName("Error");
	//
	// log.setSearchStrings(Lists.newArrayList(searchString, searchString1,
	// searchString2));
	//
	// Map<Pattern, String> replacers = new HashMap<Pattern, String>();
	//
	// FilePointer filePointer = new FilePointer();
	// filePointer.setFilename(log.getLogDirectory() + log.getLogName());
	// when(mockFilePointerProcessor.getFilePointer(anyString(),
	// anyString())).thenReturn(filePointer);
	//
	// classUnderTest = new LogMonitorTask(mockFilePointerProcessor, log,
	// replacers);
	//
	// LogMetrics result = classUnderTest.call();
	// assertEquals(log.getSearchStrings().size() + 1,
	// result.getMetrics().size());
	//
	// assertEquals(13, result.getMetrics().get("TestLog|Search
	// String|Debug|Debug").intValue());
	// assertEquals(24, result.getMetrics().get("TestLog|Search
	// String|Info|Info").intValue());
	// assertEquals(7, result.getMetrics().get("TestLog|Search
	// String|Error|Error").intValue());
	//
	// assertEquals(getFileSize(log.getLogDirectory(), log.getLogName()),
	// result.getMetrics().get("TestLog|File size (Bytes)").intValue());
	// }
	//
	// @Test
	// public void testRegexSpecialChars() throws Exception {
	// Log log = new Log();
	// log.setDisplayName("TestLog");
	// log.setLogDirectory("src/test/resources/");
	// log.setLogName("test-log-regex.log");
	//
	// SearchString searchString = new SearchString();
	// searchString.setCaseSensitive(false);
	// searchString.setMatchExactString(false);
	// searchString.setPattern("<");
	// searchString.setDisplayName("Pattern <");
	//
	// SearchString searchString1 = new SearchString();
	// searchString1.setCaseSensitive(false);
	// searchString1.setMatchExactString(false);
	// searchString1.setPattern(">");
	// searchString1.setDisplayName("Pattern >");
	//
	// SearchString searchString2 = new SearchString();
	// searchString2.setCaseSensitive(false);
	// searchString2.setMatchExactString(false);
	// searchString2.setPattern("\\*");
	// searchString2.setDisplayName("Pattern *");
	//
	// SearchString searchString3 = new SearchString();
	// searchString3.setCaseSensitive(false);
	// searchString3.setMatchExactString(false);
	// searchString3.setPattern("\\[");
	// searchString3.setDisplayName("Pattern [");
	//
	// SearchString searchString4 = new SearchString();
	// searchString4.setCaseSensitive(false);
	// searchString4.setMatchExactString(false);
	// searchString4.setPattern("\\]");
	// searchString4.setDisplayName("Pattern ]");
	//
	// SearchString searchString5 = new SearchString();
	// searchString5.setCaseSensitive(false);
	// searchString5.setMatchExactString(false);
	// searchString5.setPattern("\\.");
	// searchString5.setDisplayName("Pattern .");
	//
	// log.setSearchStrings(Lists.newArrayList(searchString, searchString1,
	// searchString2, searchString3,
	// searchString4, searchString5));
	//
	// Map<Pattern, String> replacers = new HashMap<Pattern, String>();
	//
	// FilePointer filePointer = new FilePointer();
	// filePointer.setFilename(log.getLogDirectory() + log.getLogName());
	// when(mockFilePointerProcessor.getFilePointer(anyString(),
	// anyString())).thenReturn(filePointer);
	//
	// classUnderTest = new LogMonitorTask(mockFilePointerProcessor, log,
	// replacers);
	//
	// LogMetrics result = classUnderTest.call();
	// assertEquals(log.getSearchStrings().size() + 3,
	// result.getMetrics().size());
	//
	// assertEquals(5, result.getMetrics().get("TestLog|Search String|Pattern
	// <|<").intValue());
	// assertEquals(6, result.getMetrics().get("TestLog|Search String|Pattern
	// >|>").intValue());
	// assertEquals(16, result.getMetrics().get("TestLog|Search String|Pattern
	// *|*").intValue());
	// assertEquals(23, result.getMetrics().get("TestLog|Search String|Pattern
	// [|[").intValue());
	// assertEquals(23, result.getMetrics().get("TestLog|Search String|Pattern
	// ]|]").intValue());
	// assertEquals(2, result.getMetrics().get("TestLog|Search String|Pattern
	// .|.").intValue());
	//
	// assertEquals(getFileSize(log.getLogDirectory(), log.getLogName()),
	// result.getMetrics().get("TestLog|File size (Bytes)").intValue());
	// }
	//
	// @Test
	// public void testRegexWords() throws Exception {
	// Log log = new Log();
	// log.setDisplayName("TestLog");
	// log.setLogDirectory("src/test/resources/");
	// log.setLogName("test-log-regex.log");
	//
	// SearchString searchString = new SearchString();
	// searchString.setCaseSensitive(false);
	// searchString.setMatchExactString(false);
	// searchString.setPattern("(\\s|^)m\\w+(\\s|$)");
	// searchString.setDisplayName("Pattern start with M");
	//
	// SearchString searchString1 = new SearchString();
	// searchString1.setCaseSensitive(false);
	// searchString1.setMatchExactString(false);
	// searchString1.setPattern("<\\w*>");
	// searchString1.setDisplayName("Pattern start with <");
	//
	// SearchString searchString2 = new SearchString();
	// searchString2.setCaseSensitive(false);
	// searchString2.setMatchExactString(false);
	// searchString2.setPattern("\\[JMX.*\\]");
	// searchString2.setDisplayName("Pattern start with [JMX");
	//
	// log.setSearchStrings(Lists.newArrayList(searchString, searchString1,
	// searchString2));
	//
	// Map<Pattern, String> replacers = new HashMap<Pattern, String>();
	//
	// FilePointer filePointer = new FilePointer();
	// filePointer.setFilename(log.getLogDirectory() + log.getLogName());
	// when(mockFilePointerProcessor.getFilePointer(anyString(),
	// anyString())).thenReturn(filePointer);
	//
	// classUnderTest = new LogMonitorTask(mockFilePointerProcessor, log,
	// replacers);
	//
	// LogMetrics result = classUnderTest.call();
	// assertEquals(15, result.getMetrics().size());
	//
	// // matches (\\s|^)m\\w+(\\s|$)
	// assertEquals(7,
	// result.getMetrics().get("TestLog|Search String|Pattern start with
	// M|Memorymetricgenerator").intValue());
	// assertEquals(2, result.getMetrics().get("TestLog|Search String|Pattern
	// start with M|Memory").intValue());
	// assertEquals(2, result.getMetrics().get("TestLog|Search String|Pattern
	// start with M|Major").intValue());
	// assertEquals(1, result.getMetrics().get("TestLog|Search String|Pattern
	// start with M|Mx").intValue());
	// assertEquals(1, result.getMetrics().get("TestLog|Search String|Pattern
	// start with M|Metric").intValue());
	// assertEquals(2, result.getMetrics().get("TestLog|Search String|Pattern
	// start with M|Minor").intValue());
	// assertEquals(3, result.getMetrics().get("TestLog|Search String|Pattern
	// start with M|Metrics").intValue());
	// assertEquals(1, result.getMetrics().get("TestLog|Search String|Pattern
	// start with M|Mbean").intValue());
	//
	// // matches <\\w*>
	// assertEquals(2, result.getMetrics().get("TestLog|Search String|Pattern
	// start with <|<this>").intValue());
	// assertEquals(3, result.getMetrics().get("TestLog|Search String|Pattern
	// start with <|<again>").intValue());
	//
	// // matches \\[JMX.*\\]
	// assertEquals(1,
	// result.getMetrics().get("TestLog|Search String|Pattern start with
	// [JMX|[jmxservice]").intValue());
	//
	// // "TestLog|Search String|Pattern start with <|<\w*>" -> "0"
	// // "TestLog|Search String|Pattern start with M|(\s|^)m\w+(\s|$)" -> "0"
	// assertEquals(getFileSize(log.getLogDirectory(), log.getLogName()),
	// result.getMetrics().get("TestLog|File size (Bytes)").intValue());
	// }
	//
	// @Test
	// public void testLogFileUpdatedWithMoreLogs() throws Exception {
	// String originalFilePath =
	// this.getClass().getClassLoader().getResource("test-log-1.log").getPath();
	//
	// String testFilename = "active-test-log.log";
	// String testFilepath = String.format("%s%s%s", getTargetDir().getPath(),
	// File.separator, testFilename);
	// copyFile(originalFilePath, testFilepath);
	//
	// Log log = new Log();
	// log.setDisplayName("TestLog");
	// log.setLogDirectory(getTargetDir().getPath());
	// log.setLogName(testFilename);
	//
	// SearchString searchString = new SearchString();
	// searchString.setCaseSensitive(false);
	// searchString.setMatchExactString(true);
	// searchString.setPattern("debug");
	// searchString.setDisplayName("Debug");
	//
	// SearchString searchString1 = new SearchString();
	// searchString1.setCaseSensitive(false);
	// searchString1.setMatchExactString(true);
	// searchString1.setPattern("info");
	// searchString1.setDisplayName("Info");
	//
	// SearchString searchString2 = new SearchString();
	// searchString2.setCaseSensitive(false);
	// searchString2.setMatchExactString(true);
	// searchString2.setPattern("error");
	// searchString2.setDisplayName("Error");
	//
	// log.setSearchStrings(Lists.newArrayList(searchString, searchString1,
	// searchString2));
	//
	// Map<Pattern, String> replacers = new HashMap<Pattern, String>();
	//
	// FilePointer filePointer = new FilePointer();
	// filePointer.setFilename(log.getLogDirectory() + File.separator +
	// log.getLogName());
	// when(mockFilePointerProcessor.getFilePointer(anyString(),
	// anyString())).thenReturn(filePointer);
	//
	// classUnderTest = new LogMonitorTask(mockFilePointerProcessor, log,
	// replacers);
	//
	// LogMetrics result = classUnderTest.call();
	// assertEquals(4, result.getMetrics().size());
	//
	// assertEquals(13, result.getMetrics().get("TestLog|Search
	// String|Debug|Debug").intValue());
	// assertEquals(24, result.getMetrics().get("TestLog|Search
	// String|Info|Info").intValue());
	// assertEquals(7, result.getMetrics().get("TestLog|Search
	// String|Error|Error").intValue());
	//
	// long filesize = getFileSize(log.getLogDirectory(), log.getLogName());
	// assertEquals(filesize, result.getMetrics().get("TestLog|File size
	// (Bytes)").intValue());
	//
	// // simulate our filepointer was updated
	// filePointer.updateLastReadPosition(filesize);
	// when(mockFilePointerProcessor.getFilePointer(anyString(),
	// anyString())).thenReturn(filePointer);
	//
	// // perform the update
	// List<String> logsToAdd = Arrays.asList("", new Date() + " DEBUG This is
	// the first line",
	// new Date() + " INFO This is the second line", new Date() + " INFO This is
	// the third line",
	// new Date() + " DEBUG This is the fourth line", new Date() + " DEBUG This
	// is the fifth line");
	//
	// updateLogFile(testFilepath, logsToAdd, true);
	//
	// result = classUnderTest.call();
	// assertEquals(4, result.getMetrics().size());
	// assertEquals(0, result.getMetrics().get("TestLog|Search
	// String|Error|Error").intValue());
	// assertEquals(3, result.getMetrics().get("TestLog|Search
	// String|Debug|Debug").intValue());
	// assertEquals(2, result.getMetrics().get("TestLog|Search
	// String|Info|Info").intValue());
	// }
	//
	// @Test
	// public void testLogFileRotated() throws Exception {
	// String originalFilePath =
	// this.getClass().getClassLoader().getResource("test-log-2.log").getPath();
	//
	// String testFilename = "rotated-test-log.log";
	// String testFilepath = String.format("%s%s%s", getTargetDir().getPath(),
	// File.separator, testFilename);
	// copyFile(originalFilePath, testFilepath);
	//
	// Log log = new Log();
	// log.setDisplayName("TestLog");
	// log.setLogDirectory(getTargetDir().getPath());
	// log.setLogName(testFilename);
	//
	// SearchString searchString = new SearchString();
	// searchString.setCaseSensitive(false);
	// searchString.setMatchExactString(true);
	// searchString.setPattern("trace");
	// searchString.setDisplayName("Trace");
	//
	// log.setSearchStrings(Lists.newArrayList(searchString));
	//
	// Map<Pattern, String> replacers = new HashMap<Pattern, String>();
	//
	// FilePointer filePointer = new FilePointer();
	// filePointer.setFilename(log.getLogDirectory() + File.separator +
	// log.getLogName());
	// when(mockFilePointerProcessor.getFilePointer(anyString(),
	// anyString())).thenReturn(filePointer);
	//
	// classUnderTest = new LogMonitorTask(mockFilePointerProcessor, log,
	// replacers);
	//
	// LogMetrics result = classUnderTest.call();
	// assertEquals(2, result.getMetrics().size());
	//
	// assertEquals(10, result.getMetrics().get("TestLog|Search
	// String|Trace|Trace").intValue());
	//
	// long fileSizeBeforeRotation = getFileSize(log.getLogDirectory(),
	// log.getLogName());
	// assertEquals(fileSizeBeforeRotation,
	// result.getMetrics().get("TestLog|File size (Bytes)").intValue());
	//
	// // simulate our filepointer was updated
	// filePointer.updateLastReadPosition(fileSizeBeforeRotation);
	// when(mockFilePointerProcessor.getFilePointer(anyString(),
	// anyString())).thenReturn(filePointer);
	//
	// // rotate log with these strings
	// List<String> logsToAdd = Arrays.asList("", new Date() + " TRACE This is
	// the first line",
	// new Date() + " INFO This is the second line", new Date() + " TRACE This
	// is the third line",
	// new Date() + " DEBUG This is the fourth line", new Date() + " DEBUG This
	// is the fifth line");
	//
	// updateLogFile(testFilepath, logsToAdd, false);
	//
	// result = classUnderTest.call();
	// assertEquals(2, result.getMetrics().size());
	//
	// assertEquals(2, result.getMetrics().get("TestLog|Search
	// String|Trace|Trace").intValue());
	//
	// long fileSizeAfterRotation = getFileSize(log.getLogDirectory(),
	// log.getLogName());
	// assertEquals(fileSizeAfterRotation, result.getMetrics().get("TestLog|File
	// size (Bytes)").intValue());
	//
	// assertTrue("Rotated log should've been smaller", fileSizeAfterRotation <
	// fileSizeBeforeRotation);
	// }
	//
	// @Test
	// public void testDynamicLogFileName() throws Exception {
	// String dynamicLog1 =
	// this.getClass().getClassLoader().getResource("dynamic-log-1.log").getPath();
	//
	// String testFilename = "active-dynamic-log-1.log";
	// String testFilepath = String.format("%s%s%s", getTargetDir().getPath(),
	// File.separator, testFilename);
	// copyFile(dynamicLog1, testFilepath);
	//
	// Log log = new Log();
	// log.setLogDirectory(getTargetDir().getPath());
	// log.setLogName("active-dynamic-*");
	//
	// SearchString searchString = new SearchString();
	// searchString.setCaseSensitive(false);
	// searchString.setMatchExactString(true);
	// searchString.setPattern("debug");
	// searchString.setDisplayName("Debug");
	//
	// SearchString searchString1 = new SearchString();
	// searchString1.setCaseSensitive(false);
	// searchString1.setMatchExactString(true);
	// searchString1.setPattern("error");
	// searchString1.setDisplayName("Error");
	//
	// log.setSearchStrings(Lists.newArrayList(searchString, searchString1));
	//
	// Map<Pattern, String> replacers = new HashMap<Pattern, String>();
	//
	// FilePointer filePointer = new FilePointer();
	// filePointer.setFilename(log.getLogDirectory() + File.separator +
	// testFilename);
	// when(mockFilePointerProcessor.getFilePointer(anyString(),
	// anyString())).thenReturn(filePointer);
	// classUnderTest = new LogMonitorTask(mockFilePointerProcessor, log,
	// replacers);
	// LogMetrics result = classUnderTest.call();
	// assertEquals(3, result.getMetrics().size());
	// assertEquals(3, result.getMetrics().get("active-dynamic-*|Search
	// String|Debug|Debug").intValue());
	// assertEquals(0, result.getMetrics().get("active-dynamic-*|Search
	// String|Error|Error").intValue());
	// long filesize = getFileSize(log.getLogDirectory(), testFilename);
	// assertEquals(filesize, result.getMetrics().get("active-dynamic-*|File
	// size (Bytes)").intValue());
	// // simulate our filepointer was updated
	// filePointer.updateLastReadPosition(filesize);
	// when(mockFilePointerProcessor.getFilePointer(anyString(),
	// anyString())).thenReturn(filePointer);
	// // simulate new file created with different name
	// Thread.sleep(1000);
	// String dynamicLog2 =
	// this.getClass().getClassLoader().getResource("dynamic-log-2.log").getPath();
	// testFilename = "active-dynamic-log-2.log";
	// testFilepath = String.format("%s%s%s", getTargetDir().getPath(),
	// File.separator, testFilename);
	// copyFile(dynamicLog2, testFilepath);
	// result = classUnderTest.call();
	// assertEquals(3, result.getMetrics().size());
	// assertEquals(7, result.getMetrics().get("active-dynamic-*|Search
	// String|Error|Error").intValue());
	// assertEquals(0, result.getMetrics().get("active-dynamic-*|Search
	// String|Debug|Debug").intValue());
	// filesize = getFileSize(log.getLogDirectory(), testFilename);
	// assertEquals(filesize, result.getMetrics().get("active-dynamic-*|File
	// size (Bytes)").intValue());
	// }
	//
	// private long getFileSize(String logDir, String logName) throws Exception
	// {
	// String fullPath = String.format("%s%s%s", logDir, File.separator,
	// logName);
	// RandomAccessFile file = new RandomAccessFile(fullPath, "r");
	// long fileSize = file.length();
	// file.close();
	// return fileSize;
	// }
	//
	// @SuppressWarnings("resource")
	// private void copyFile(String sourceFilePath, String destFilePath) throws
	// Exception {
	// FileChannel sourceChannel = null;
	// FileChannel destChannel = null;
	// try {
	// sourceChannel = new FileInputStream(new
	// File(sourceFilePath)).getChannel();
	// destChannel = new FileOutputStream(new File(destFilePath)).getChannel();
	// destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	// } finally {
	// sourceChannel.close();
	// destChannel.close();
	// }
	// }
	//
	// private void updateLogFile(String filepath, List<String> stringList,
	// boolean append) throws Exception {
	// File file = new File(filepath);
	// FileWriter fileWriter = null;
	//
	// try {
	// fileWriter = new FileWriter(file, append);
	// String output = StringUtils.join(stringList,
	// System.getProperty("line.separator"));
	// fileWriter.write(output);
	//
	// } finally {
	// fileWriter.close();
	// }
	// }
	//
	// private File getTargetDir() {
	// return new File("./target");
	// }

}
