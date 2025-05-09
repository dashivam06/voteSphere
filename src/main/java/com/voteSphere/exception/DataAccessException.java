package com.voteSphere.exception;

public class DataAccessException extends DatabaseException {
	private static final long serialVersionUID = -1815503599347712109L;

	public DataAccessException(String message, String userMessage, Throwable cause) {
		super(message, userMessage, cause);
	}
}