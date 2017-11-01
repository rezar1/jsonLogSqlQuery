package com.extensions.logmonitor.util;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年11月1日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class BatchTimeWatcher {

	private BatchWatchOutput batchWatchOutput;
	private int batchCount;
	private int currentCount;
	private long preTime;
	private long currentTime;
	private int batchTimeSum;
	private int batchIndex;

	public BatchTimeWatcher(int batchCount, BatchWatchOutput batchWatchOutput) {
		this.batchCount = batchCount;
		this.batchWatchOutput = batchWatchOutput;
	}

	public void countSingleTimeStart() {
		this.preTime = System.currentTimeMillis();
	}

	public void countSingleTimeEnd() {
		this.currentCount++;
		this.currentTime = System.currentTimeMillis();
		this.batchTimeSum += (this.currentTime - this.preTime);
		if (this.currentCount == this.batchCount) {
			this.batchIndex++;
			batchWatchOutput.output(this.batchIndex, batchCount, batchTimeSum, preTime, currentTime);
			this.batchTimeSum = 0;
			this.currentCount = 0;
		}
	}

	public static interface BatchWatchOutput {
		public void output(int batchIndex, int batchCount, int batchUseTime, long preTime, long currentTime);
	}

}
