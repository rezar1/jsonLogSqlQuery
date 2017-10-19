package com.extensions.logmonitor.logFileAnalyzer;

import static com.extensions.logmonitor.Constants.METRIC_PATH_SEPARATOR;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.bitbucket.kienerj.OptimizedRandomAccessFile;

import com.extensions.logmonitor.Constants;
import com.extensions.logmonitor.MultiLogAnalyzerResult;
import com.extensions.logmonitor.config.LogJsonAnalyzer;
import com.extensions.logmonitor.exceptions.FileException;
import com.extensions.logmonitor.jsonLogModule.jsonLogSelectParser.JsonLogDataQueryHandler;
import com.extensions.logmonitor.processors.FilePointer;
import com.extensions.logmonitor.processors.FilePointerProcessor;
import com.extensions.logmonitor.util.LogMonitorUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogMonitorTaskForJsonAnalyzer implements Callable<MultiLogAnalyzerResult> {

	private FilePointerProcessor filePointerProcessor;
	private LogJsonAnalyzer logJsonAnalyzer;

	// private Map<String, String> logEventTypes = new HashMap<>();
	private Set<String> handleOverLogEventTypes = new HashSet<>();

	public LogMonitorTaskForJsonAnalyzer(FilePointerProcessor filePointerProcessor, LogJsonAnalyzer logJsonAnalyzer) {
		this.filePointerProcessor = filePointerProcessor;
		this.logJsonAnalyzer = logJsonAnalyzer;
	}

	public MultiLogAnalyzerResult call() throws Exception {
		String dirPath = resolveDirPath(logJsonAnalyzer.getLogDirectory());
		log.info("Log monitor task started...");
		MultiLogAnalyzerResult logMetrics = new MultiLogAnalyzerResult();
		OptimizedRandomAccessFile randomAccessFile = null;
		long curFilePointer = 0;
		try {
			File file = getLogFile(dirPath);
			randomAccessFile = new OptimizedRandomAccessFile(file, "r");
			long fileSize = randomAccessFile.length();
			String dynamicLogPath = dirPath + logJsonAnalyzer.getLogName();
			curFilePointer = getCurrentFilePointer(dynamicLogPath, file.getPath(), fileSize);
			randomAccessFile.seek(curFilePointer);
			String currentLine = null;
			watch.start();
			watch.split();
			while ((currentLine = randomAccessFile.readLine()) != null) {
				handleLine(currentLine, curFilePointer);
				curFilePointer = randomAccessFile.getFilePointer();
			}
			watch.stop();
			System.out.println("scan all line use time:" + watch.getSplitTime());
			logMetrics.add(getLogNamePrefix() + Constants.FILESIZE_METRIC_NAME, BigInteger.valueOf(fileSize));
			setNewFilePointer(dynamicLogPath, file.getPath(), curFilePointer);
			analyzerQuery();
			log.info(String.format("Successfully processed log file [%s] -- %s", file.getPath(), curFilePointer));
		} finally {
			LogMonitorUtil.closeRandomAccessFile(randomAccessFile);
		}
		return logMetrics;
	}

	/**
	 * 
	 */
	private void analyzerQuery() {
		Set<String> allHandleLogEventTypes = this.logJsonAnalyzer.getAllHandleLogEventTypes();
		for (String logEventType : allHandleLogEventTypes) {
			JsonLogDataQueryHandler findJsonLogDataQueryHandler = this.logJsonAnalyzer
					.findJsonLogDataQueryHandler(logEventType);
			findJsonLogDataQueryHandler.doAnalyzerResult();
		}
	}

	public static void main(String[] args) {
		String currentLine = "{\"LogEventType\":\"AdReqLog\"}";
		int indexOf = currentLine.indexOf("\"LogEventType\":");
		if (indexOf == -1) {
			return;
		}
		String logEventTypeStr = currentLine.substring(indexOf + 16, currentLine.indexOf("\"", indexOf + 17));
		System.out.println(logEventTypeStr);
	}

	private static final StopWatch watch = new StopWatch();

	/**
	 * @param currentLine
	 * @param curFilePointer
	 */
	private void handleLine(String currentLine, long curFilePointer) {
		int indexOf = currentLine.indexOf("logEventType");
		if (indexOf == -1) {
			return;
		}
		String logEventTypeStr = currentLine.substring(indexOf + 15, currentLine.indexOf("\"", indexOf + 17));
		JsonLogDataQueryHandler jsonLogDataQueryHandler = this.logJsonAnalyzer
				.findJsonLogDataQueryHandler(logEventTypeStr);
		if (jsonLogDataQueryHandler != null) {
			jsonLogDataQueryHandler.wirteString(currentLine);
		}
	}

	private File getLogFile(String dirPath) throws FileNotFoundException {
		File directory = new File(dirPath);
		File logFile = null;
		if (directory.isDirectory()) {
			FileFilter fileFilter = new WildcardFileFilter(logJsonAnalyzer.getLogName());
			File[] files = directory.listFiles(fileFilter);
			if (files != null && files.length > 0) {
				logFile = getLatestFile(files);
				if (!logFile.canRead()) {
					throw new FileException(String.format("Unable to read file [%s]", logFile.getPath()));
				}
			} else {
				throw new FileNotFoundException(String.format("Unable to find any file with name [%s] in [%s]",
						logJsonAnalyzer.getLogName(), dirPath));
			}
		} else {
			throw new FileNotFoundException(
					String.format("Directory [%s] not found. Ensure it is a directory.", dirPath));
		}
		return logFile;
	}

	private String resolveDirPath(String confDirPath) {
		String resolvedPath = LogMonitorUtil.resolvePath(confDirPath);
		if (!resolvedPath.endsWith(File.separator)) {
			resolvedPath = resolvedPath + File.separator;
		}
		return resolvedPath;
	}

	private File getLatestFile(File[] files) {
		File latestFile = null;
		long lastModified = Long.MIN_VALUE;
		for (File file : files) {
			if (file.lastModified() > lastModified) {
				latestFile = file;
				lastModified = file.lastModified();
			}
		}
		return latestFile;
	}

	private long getCurrentFilePointer(String dynamicLogPath, String actualLogPath, long fileSize) {
		FilePointer filePointer = filePointerProcessor.getFilePointer(dynamicLogPath, actualLogPath);
		long currentPosition = filePointer.getLastReadPosition().get();
		if (isFilenameChanged(filePointer.getFilename(), actualLogPath) || isLogRotated(fileSize, currentPosition)) {
			if (log.isDebugEnabled()) {
				log.debug("Filename has either changed or rotated, resetting position to 0");
			}
			currentPosition = 0;
		}
		return currentPosition;
	}

	private boolean isLogRotated(long fileSize, long startPosition) {
		return fileSize < startPosition;
	}

	private boolean isFilenameChanged(String oldFilename, String newFilename) {
		return !oldFilename.equals(newFilename);
	}

	private void setNewFilePointer(String dynamicLogPath, String actualLogPath, long lastReadPosition) {
		filePointerProcessor.updateFilePointer(dynamicLogPath, actualLogPath, lastReadPosition);
	}

	private String getLogNamePrefix() {
		String displayName = StringUtils.isBlank(logJsonAnalyzer.getDisplayName()) ? logJsonAnalyzer.getLogName()
				: logJsonAnalyzer.getDisplayName();
		return displayName + METRIC_PATH_SEPARATOR;
	}

}
