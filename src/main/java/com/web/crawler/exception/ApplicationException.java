package com.web.crawler.exception;


public class ApplicationException extends RuntimeException {
	private static final long serialVersionUID = 6347675023685108688L;

	
	public ApplicationException() {
		super("Error in Web crawler.");
	}

	/**
	 * Instantiates a new app exception.
	 *
	 * @param message the message
	 */
	public ApplicationException(final String message) {
		super(message);
	}

	/**
	 * Instantiates a new app exception.
	 *
	 * @param message the message
	 * @param tr      the Throwable
	 */
	public ApplicationException(final String message, final Throwable tr) {
		super(message, tr);
	}

}