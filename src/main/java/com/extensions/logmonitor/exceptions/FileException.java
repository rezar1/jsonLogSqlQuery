package com.extensions.logmonitor.exceptions;

/**
 * @author Florencio Sarmiento
 *
 */
public class FileException extends RuntimeException {

	private static final long serialVersionUID = -386911531228438232L;

	public FileException() {
	}

	public FileException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileException(String message) {
		super(message);
	}

	public FileException(Throwable cause) {
		super(cause);
	}

}
