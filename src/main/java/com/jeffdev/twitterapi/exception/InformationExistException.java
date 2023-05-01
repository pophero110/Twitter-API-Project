package com.jeffdev.twitterapi.exception;

/**
 * Exception thrown when attempting to create or update information that already exists.
 */
public class InformationExistException extends RuntimeException {
    public InformationExistException(String message) {
        super(message);
    }
}
