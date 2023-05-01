package com.jeffdev.twitterapi.model.response;


import com.jeffdev.twitterapi.model.violation.Violation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();

    public List<Violation> getViolations() {
        return violations;
    }
}
