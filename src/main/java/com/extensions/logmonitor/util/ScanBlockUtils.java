package com.extensions.logmonitor.util;

import java.io.File;
import java.io.FileNotFoundException;
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
public class ScanBlockUtils {

	public static List<ScanBlockIndex> computeBlockIndex(String fileName, long blockSize) {
		List<ScanBlockIndex> indexes = new ArrayList<>();
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				throw new FileNotFoundException(fileName);
			}
			OptimizedRandomAccessFile randomAccessFile = new OptimizedRandomAccessFile(file, "r");
			long length = randomAccessFile.length();
			System.out.println("fileLength:" + length + "blockSize:" + blockSize + "  for fileName:" + fileName);
			long currentPoint = 0;
			long beginPoint = 0;
			boolean eof = false;
			while (!eof) {
				beginPoint = currentPoint;
				currentPoint = currentPoint + blockSize;
				if (currentPoint < length) {
					randomAccessFile.seek(currentPoint);
					int read = randomAccessFile.read();
					while (read != -1 && !(read == '\n') || (read == '\r')) {
						currentPoint += 1;
						read = randomAccessFile.read();
					}
					if (read == -1) {
						eof = true;
					} else {
						currentPoint = currentPoint + 1;
					}
				} else {
					currentPoint = length;
					eof = true;
				}
				indexes.add(new ScanBlockIndex(beginPoint, currentPoint));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return indexes;
	}

	public static List<ScanBlockIndex> computeBlockIndex(String fileName) {
		return computeBlockIndex(fileName, 2147483648l); // 2G
	}

	public static void main(String[] args) {
		String filename = "/Users/rezar/RezarWorkSpace/eclipseWorkSpcae/log/logFiles/test.log";
		List<ScanBlockIndex> computeBlockIndex = computeBlockIndex(filename);
		System.out.println(computeBlockIndex);
	}

}
