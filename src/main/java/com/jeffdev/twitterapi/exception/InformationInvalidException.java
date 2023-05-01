package com.jeffdev.twitterapi.exception;

/**
 * Exception thrown when attempting to create or update information that is invalid
 */
public class InformationInvalidException extends RuntimeException {
    public InformationInvalidException(String message) {
        super(message);
    }
}
