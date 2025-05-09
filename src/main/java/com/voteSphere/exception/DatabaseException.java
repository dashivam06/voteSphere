package com.voteSphere.exception;

//Database-related exceptions
public class DatabaseException extends VoteSphereException {
	private static final long serialVersionUID = -4180531780362005056L;

	public DatabaseException(String message, String userMessage, Throwable cause) {
		super(message, userMessage, cause);
	}
	
	public DatabaseException(String message, Throwable cause) {
		super(message, "Something went wrong. Please try again later.", cause);
	}
}





