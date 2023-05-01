package com.jeffdev.twitterapi.exception;

/**
 * Exception thrown when attempting to a record is not found in database
 */
public class InformationNotFoundException extends RuntimeException {
    public InformationNotFoundException(String message) {
        super(message);
    }
}
