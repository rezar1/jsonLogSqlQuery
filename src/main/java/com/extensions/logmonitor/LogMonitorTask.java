package com.extensions.logmonitor;

import static com.extensions.logmonitor.Constants.FILESIZE_METRIC_NAME;
import static com.extensions.logmonitor.Constants.METRIC_PATH_SEPARATOR;
import static com.extensions.logmonitor.Constants.SEARCH_STRING;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.log4j.Logger;
import org.bitbucket.kienerj.OptimizedRandomAccessFile;

import com.extensions.logmonitor.config.Log;
import com.extensions.logmonitor.exceptions.FileException;
import com.extensions.logmonitor.processors.FilePointer;
import com.extensions.logmonitor.processors.FilePointerProcessor;
import com.extensions.logmonitor.util.LogMonitorUtil;

public class LogMonitorTask implements Callable<LogMetrics> {

	private static final Logger LOGGER = Logger.getLogger(LogMonitorTask.class);
	private FilePointerProcessor filePointerProcessor;
	private Log log;
	private Map<Pattern, String> replacers;

	public LogMonitorTask(FilePointerProcessor filePointerProcessor, Log log, Map<Pattern, String> replacers) {
		this.filePointerProcessor = filePointerProcessor;
		this.log = log;
		this.replacers = replacers;
	}

	public LogMetrics call() throws Exception {
		String dirPath = resolveDirPath(log.getLogDirectory());
		LOGGER.info("Log monitor task started...");
		LogMetrics logMetrics = new LogMetrics();
		OptimizedRandomAccessFile randomAccessFile = null;
		long curFilePointer = 0;
		try {
			File file = getLogFile(dirPath);
			randomAccessFile = new OptimizedRandomAccessFile(file, "r");
			long fileSize = randomAccessFile.length();
			String dynamicLogPath = dirPath + log.getLogName();
			curFilePointer = getCurrentFilePointer(dynamicLogPath, file.getPath(), fileSize);
			List<SearchPattern> searchPatterns = LogMonitorUtil.createPattern(log.getSearchStrings());
			LOGGER.info(String.format("Processing log file [%s], starting from [%s]", file.getPath(), curFilePointer));
			randomAccessFile.seek(curFilePointer);
			String currentLine = null;
			if (LOGGER.isDebugEnabled()) {
				for (SearchPattern searchPattern : searchPatterns) {
					LOGGER.debug(String.format("Searching for [%s]", searchPattern.getPattern().pattern()));
				}
			}
			while ((currentLine = randomAccessFile.readLine()) != null) {
				incrementWordCountIfSearchStringMatched(searchPatterns, currentLine, logMetrics);
				curFilePointer = randomAccessFile.getFilePointer();
			}
			logMetrics.add(getLogNamePrefix() + FILESIZE_METRIC_NAME, BigInteger.valueOf(fileSize));
			setNewFilePointer(dynamicLogPath, file.getPath(), curFilePointer);
			LOGGER.info(String.format("Successfully processed log file [%s]", file.getPath()));
		} finally {
			LogMonitorUtil.closeRandomAccessFile(randomAccessFile);
		}
		return logMetrics;
	}

	private File getLogFile(String dirPath) throws FileNotFoundException {
		File directory = new File(dirPath);
		File logFile = null;
		if (directory.isDirectory()) {
			FileFilter fileFilter = new WildcardFileFilter(log.getLogName());
			File[] files = directory.listFiles(fileFilter);
			if (files != null && files.length > 0) {
				logFile = getLatestFile(files);
				if (!logFile.canRead()) {
					throw new FileException(String.format("Unable to read file [%s]", logFile.getPath()));
				}
			} else {
				throw new FileNotFoundException(
						String.format("Unable to find any file with name [%s] in [%s]", log.getLogName(), dirPath));
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
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("Filename has either changed or rotated, resetting position to 0");
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

	private void incrementWordCountIfSearchStringMatched(List<SearchPattern> searchPatterns, String stringToCheck,
			LogMetrics logMetrics) {
		for (SearchPattern searchPattern : searchPatterns) {
			Boolean isPresent = false;
			Matcher matcher = searchPattern.getPattern().matcher(stringToCheck);
			String logMetricPrefix = getSearchStringPrefix();
			while (matcher.find()) {
				isPresent = true;
				String word = matcher.group().trim();
				String replacedWord = applyReplacers(word);
				if (searchPattern.getCaseSensitive()) {
					logMetrics.add(
							logMetricPrefix + searchPattern.getDisplayName() + METRIC_PATH_SEPARATOR + replacedWord);
				} else {
					logMetrics.add(logMetricPrefix + searchPattern.getDisplayName() + METRIC_PATH_SEPARATOR
							+ WordUtils.capitalizeFully(replacedWord));
				}
			}
			if (!isPresent) {
				String metricPrefix = getSearchStringPrefix() + searchPattern.getDisplayName() + METRIC_PATH_SEPARATOR;
				List<String> metricNames = LogMonitorUtil.getNamesFromSearchStrings(log.getSearchStrings());
				String patternName = searchPattern.getCaseSensitive()
						? applyReplacers(searchPattern.getPattern().pattern().trim())
						: WordUtils.capitalizeFully(applyReplacers(searchPattern.getPattern().pattern().trim()));
				for (String metricName : metricNames) {
					if (StringUtils.containsIgnoreCase(patternName, metricName)
							&& !logMetrics.getMetrics().containsKey(metricPrefix + metricName)) {
						logMetrics.add(metricPrefix + metricName, BigInteger.ZERO);
					}
				}
			}
		}
	}

	private void setNewFilePointer(String dynamicLogPath, String actualLogPath, long lastReadPosition) {
		filePointerProcessor.updateFilePointer(dynamicLogPath, actualLogPath, lastReadPosition);
	}

	private String getSearchStringPrefix() {
		return String.format("%s%s%s", getLogNamePrefix(), SEARCH_STRING, METRIC_PATH_SEPARATOR);
	}

	private String getLogNamePrefix() {
		String displayName = StringUtils.isBlank(log.getDisplayName()) ? log.getLogName() : log.getDisplayName();
		return displayName + METRIC_PATH_SEPARATOR;
	}

	private String applyReplacers(String name) {
		if (name == null || name.length() == 0 || replacers == null) {
			return name;
		}
		for (Map.Entry<Pattern, String> replacerEntry : replacers.entrySet()) {
			Pattern pattern = replacerEntry.getKey();
			Matcher matcher = pattern.matcher(name);
			name = matcher.replaceAll(replacerEntry.getValue());
		}
		return name;
	}
}
