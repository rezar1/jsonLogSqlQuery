package com.extensions.logmonitor;

/**
 * @author Florencio Sarmiento
 *
 */
public final class Constants {
	
	public static final String CONFIG_ARG = "config-file";
	
	public static final String FILEPOINTER_FILENAME = "filepointer.json";
	
	public static final String METRIC_PATH_SEPARATOR = "|";
	
	public static final String DEFAULT_METRIC_PATH = String.format("%s%s%s%s", "Custom Metrics", 
			METRIC_PATH_SEPARATOR, "LogMonitor", METRIC_PATH_SEPARATOR);
	
	public static final String SEARCH_STRING = "Search String";
	
	public static final String FILESIZE_METRIC_NAME = "File size (Bytes)";
	
	public static final int DEFAULT_NO_OF_THREADS = 3;
	
	public static final int THREAD_TIMEOUT = 60;

	public static final String REGEX = "[^A-Za-z0-9]";
}
