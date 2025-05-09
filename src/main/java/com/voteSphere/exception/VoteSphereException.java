package com.voteSphere.exception;

public class VoteSphereException extends RuntimeException 
{

	private static final long serialVersionUID = -2716091329720036598L;
	private final String userMessage;
    
    public VoteSphereException(String message, String userMessage, Throwable cause) {
        super(message, cause);
        this.userMessage = userMessage;
    }
    
    public String getUserMessage() {
        return userMessage;
    }
}