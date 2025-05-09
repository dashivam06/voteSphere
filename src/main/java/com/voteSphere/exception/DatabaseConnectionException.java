package com.voteSphere.exception;

public class DatabaseConnectionException extends DatabaseException {
	private static final long serialVersionUID = 6630571600340116514L;

	public DatabaseConnectionException(String message, Throwable cause) {
		super(message, "Unable to connect to database. Please try again later.", cause);
	}
}