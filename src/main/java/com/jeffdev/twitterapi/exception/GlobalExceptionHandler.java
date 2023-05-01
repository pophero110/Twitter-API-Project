package com.jeffdev.twitterapi.exception;

import com.jeffdev.twitterapi.model.response.ErrorResponse;
import com.jeffdev.twitterapi.model.response.ValidationErrorResponse;
import com.jeffdev.twitterapi.model.violation.Violation;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;


/**
 * This class serves as a global exception handler for the entire application.
 * It contains methods to handle different types of exceptions and return appropriate responses.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles ConstraintViolationException and returns a ValidationErrorResponse.
     *
     * @param e the ConstraintViolationException object to be handled
     * @return a ValidationErrorResponse object containing the details of the validation errors
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onConstraintValidationException(
            ConstraintViolationException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            error.getViolations().add(
                    new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
        }
        return error;
    }

    /**
     * Handles MethodArgumentNotValidException and returns a ValidationErrorResponse.
     *
     * @param e the MethodArgumentNotValidException object to be handled
     * @return a ValidationErrorResponse object containing the details of the validation errors
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    ValidationErrorResponse onMethodArgumentNotValidException(
            MethodArgumentNotValidException e) {
        ValidationErrorResponse error = new ValidationErrorResponse();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            error.getViolations().add(
                    new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        }
        return error;
    }

    /**
     * Handles InformationNotFoundException and returns an ErrorResponse.
     *
     * @param e the InformationNotFoundException object to be handled
     * @return an ErrorResponse object containing the error message
     */
    @ExceptionHandler(InformationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    ErrorResponse onInformationNotFoundException(
            InformationNotFoundException e) {
        return new ErrorResponse(e.getMessage());
    }

    /**
     * Handles InformationExistException and returns an ErrorResponse.
     *
     * @param e the InformationExistException object to be handled
     * @return an ErrorResponse object containing the error message
     */
    @ExceptionHandler(InformationExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    ErrorResponse onInformationExistException(
            InformationExistException e) {
        return new ErrorResponse(e.getMessage());
    }
}
