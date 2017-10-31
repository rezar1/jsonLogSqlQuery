package com.extensions.logmonitor.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.extensions.logmonitor.config.LogJsonAnalyzer;
import com.extensions.logmonitor.config.SearchInfo;
import com.extensions.logmonitor.logFileAnalyzer.LogMonitorTaskForJsonAnalyzer;
import com.extensions.logmonitor.processors.FilePointerProcessor;
import com.extensions.logmonitor.util.GenericsUtils;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月12日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class MainInvoke {

	private static FilePointerProcessor filePointerProcessor;

	public static void main(String[] args) throws Exception {
		if (args == null || args.length < 1) {
			System.out.println("need request param:\n1:configReadPath eg:/Users/rezar/Desktop/sspCount.conf");
			System.exit(1);
		}

		String configReadPath = args[0];
		String[] configs = parserFile(configReadPath);
		if (GenericsUtils.isNullOrEmpty(configs)) {
			throw new IllegalArgumentException("can not find any config in file:" + configReadPath);
		}
		String logDirectory = changeFileString(configs[0]);
		
		String logName = configs[1];
		logDirectory = changeFileString(logDirectory);
		String logSql = configs[2];
		System.out.println("logDirectory is:" + logDirectory);
		System.out.println("logFileName is:" + logName);
		System.out.println("logFileQuerySql is:" + logSql);
		filePointerProcessor = new FilePointerProcessor();
		LogJsonAnalyzer logJsonAnalyzer = new LogJsonAnalyzer("Rezar", logDirectory, logName);
		SearchInfo searchInfo = new SearchInfo(logSql);
		logJsonAnalyzer.addSearchInfo(searchInfo);
		LogMonitorTaskForJsonAnalyzer analyzer = new LogMonitorTaskForJsonAnalyzer(filePointerProcessor,
				logJsonAnalyzer);
		analyzer.call();
	}

	/**
	 * @param configReadPath
	 * @return
	 */
	private static String[] parserFile(String configReadPath) {
		File file = new File(configReadPath);
		if (!file.exists()) {
			throw new IllegalArgumentException(configReadPath + " \t not exists!!!");
		}
		BufferedReader reader = null;
		List<String> configs = new ArrayList<>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null) {
				configs.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException(configReadPath + "\t can not read!!!");
		} finally {
			if (reader != null) {
				IOUtils.closeQuietly(reader);
			}
		}
		return configs.toArray(new String[configs.size()]);
	}

	/**
	 * @param args
	 * @return
	 */
	// private static String createQuerySql(String[] args) {
	// StringBuilder sb = new StringBuilder();
	// for (int i = 2; i < args.length; i++) {
	// sb.append(args[i]).append(" ");
	// }
	// return sb.toString();
	// }

	/**
	 * @param logDirectory
	 * @return
	 */
	private static String changeFileString(String filePathStr) {
		if (!filePathStr.endsWith("/")) {
			filePathStr += File.separator;
		}
		return filePathStr;
	}

}
