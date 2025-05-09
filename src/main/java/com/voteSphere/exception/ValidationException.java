package com.voteSphere.exception;

public class ValidationException extends VoteSphereException {
    private static final long serialVersionUID = -2968236106771656195L;

	public ValidationException(String message, String userMessage) {
        super(message, userMessage, null);
    }
}