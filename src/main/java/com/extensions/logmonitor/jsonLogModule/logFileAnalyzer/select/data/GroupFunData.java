package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.select.data;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.TupleUtil;
import com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.dataCache.BPlusDataCache.TwoTuple;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月21日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class GroupFunData<T1, T2> {

	private ReadWriteLock lock = null;
	private Lock writeLock;
	private Lock readLock;

	private T1 data1;
	private T2 data2;

	public GroupFunData() {
		this(null, null);
	}

	public GroupFunData(T1 data1, T2 data2) {
		this.data1 = data1;
		this.data2 = data2;
		this.initLock();
	}

	public void initLock() {
		this.lock = new ReentrantReadWriteLock(true);
		this.writeLock = lock.writeLock();
		this.readLock = lock.readLock();

	}

	public void safeSetDatas(DoInLockCallback<T1, T2> doInLockCallback) {
		this.writeLock.lock();
		try {
			doInLockCallback.doInLock(data1, data2);
		} finally {
			this.writeLock.unlock();
		}
	}

	public void unsafeSetDatas(T1 data1, T2 data2) {
		this.data1 = data1;
		this.data2 = data2;
	}

	public TwoTuple<T1, T2> safeReadDatas() {
		this.readLock.lock();
		try {
			return TupleUtil.tuple(data1, data2);
		} finally {
			this.readLock.unlock();
		}
	}

	public static interface DoInLockCallback<T1, T2> {
		public void doInLock(T1 data1, T2 data2);
	}

}
