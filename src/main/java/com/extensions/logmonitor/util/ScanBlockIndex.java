package com.extensions.logmonitor.util;

import lombok.Data;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年11月2日
 * @Desc this guy is to lazy , noting left.
 *
 */
@Data
public class ScanBlockIndex {

	/**
	 * @param beginPoint
	 * @param currentPoint
	 */
	public ScanBlockIndex(long beginPoint, long currentPoint) {
		this.beginIndex = beginPoint;
		this.endIndex = currentPoint;
	}

	private long beginIndex;
	private long endIndex;

}
