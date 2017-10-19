package com.extensions.logmonitor.processors;

import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author Florencio Sarmiento
 *
 */
public class FilePointer {

	private volatile String filename;

	private AtomicLong lastReadPosition = new AtomicLong(0);

	public String getFilename() {
		return filename;
	}

	public synchronized void setFilename(String filename) {
		this.filename = filename;
	}

	public AtomicLong getLastReadPosition() {
		return lastReadPosition;
	}

	public synchronized void setLastReadPosition(AtomicLong lastReadPosition) {
		this.lastReadPosition = lastReadPosition;
	}

	public synchronized void updateLastReadPosition(long lastReadPosition) {
		if (this.lastReadPosition == null) {
			this.lastReadPosition = new AtomicLong(lastReadPosition);
		} else {
			this.lastReadPosition.set(lastReadPosition);
		}
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
