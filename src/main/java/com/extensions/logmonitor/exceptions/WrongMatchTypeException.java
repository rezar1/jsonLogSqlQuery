package com.extensions.logmonitor.exceptions;

public class WrongMatchTypeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String format = "OptType:%s ,want to match :%s with value :%s , but type mistack";

	public WrongMatchTypeException(String optType, Object matchValue, Object wantToMatchValue) {
		super(String.format(format, optType, matchValue, wantToMatchValue));
	}
}
