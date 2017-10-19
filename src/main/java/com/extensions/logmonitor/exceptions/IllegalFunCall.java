package com.extensions.logmonitor.exceptions;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年9月13日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class IllegalFunCall extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalFunCall(String errorInfo) {
		super(errorInfo);
	}

}
