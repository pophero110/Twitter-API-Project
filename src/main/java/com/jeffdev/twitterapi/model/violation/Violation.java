package com.jeffdev.twitterapi.model.violation;

/**
 * A class representing a validation error message for a specific field.
 */
public class Violation {

    private final String fieldName;
    private final String message;

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getMessage() {
        return message;
    }
}
