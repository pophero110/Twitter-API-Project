package com.jeffdev.twitterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InformationBlankException extends RuntimeException {
    public InformationBlankException(String message) {
        super(message);
    }
}
