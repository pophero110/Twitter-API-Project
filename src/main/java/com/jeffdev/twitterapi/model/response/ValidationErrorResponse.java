package com.jeffdev.twitterapi.model.response;


import com.jeffdev.twitterapi.model.violation.Violation;

import java.util.ArrayList;
import java.util.List;

/**
 * A response class that represents a list of validation errors.
 */
public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }
}
