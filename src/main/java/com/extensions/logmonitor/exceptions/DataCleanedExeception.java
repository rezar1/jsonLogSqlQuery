package com.extensions.logmonitor.exceptions;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月21日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class DataCleanedExeception extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataCleanedExeception(long offset) {
		super("data from pos : " + offset + " has cleaned");
	}

}
