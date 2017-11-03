package com.extensions.logmonitor.config;

import java.io.File;
import java.util.UUID;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月31日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class CommonConfig {

	public static File tempFilePath;
	public static int watchBatchSize = 1000000;
	public static String defaultLogEventType = "test";

	static {
		tempFilePath = new File("./jsonLogQuery_Temp_" + UUID.randomUUID().toString().replaceAll("-", ""));
		if (!tempFilePath.exists()) {
			tempFilePath.mkdirs();
		}
	}

}
