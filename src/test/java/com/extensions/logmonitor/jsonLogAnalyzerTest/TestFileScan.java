package com.extensions.logmonitor.jsonLogAnalyzerTest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bitbucket.kienerj.OptimizedRandomAccessFile;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年11月2日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class TestFileScan {

	public static void main(String[] args) throws IOException {
		File file = new File("/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles/test.log");
		OptimizedRandomAccessFile randomAccessFile = new OptimizedRandomAccessFile(file, "r");
		long length = randomAccessFile.length();
		System.out.println("length:" + length);
		List<Long> blockIndex = new ArrayList<>();
		long blockSize = 2147483648l;
		System.out.println("blockSize:" + blockSize);
		long currentPoint = 0;
		while (true) {
			blockIndex.add(currentPoint);
			currentPoint = currentPoint + blockSize;
			System.out.println("currentPoint:" + currentPoint);
			if (currentPoint >= length) {
				break;
			}
			randomAccessFile.seek(currentPoint);
			int read = randomAccessFile.read();
			while (read != -1 && !(read == '\n') || (read == '\r')) {
				currentPoint += 1;
				read = randomAccessFile.read();
			}
			if (read == -1) {
				break;
			}
			currentPoint = currentPoint + 1;
		}
		System.out.println("AllBegin are:" + blockIndex);
		randomAccessFile.seek(blockIndex.get(blockIndex.size() - 1));
		System.out.println(randomAccessFile.readLine());

	}

}
