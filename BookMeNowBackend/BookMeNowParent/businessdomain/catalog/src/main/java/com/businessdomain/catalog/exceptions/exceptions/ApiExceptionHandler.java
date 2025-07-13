package com.businessdomain.catalog.exceptions.exceptions;


import com.businessdomain.catalog.exceptions.common.StandardizeApiExceptionResponse;
import com.businessdomain.user.exceptions.common.StandardizedApiExceptionResponse;
import com.businessdomain.user.exceptions.exceptions.BusinessRuleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class ApiExceptionHandler {

    /**
     * Handler validation errors
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardizeApiExceptionResponse> handlerValidationException(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.joining("; "));

        StandardizeApiExceptionResponse response = new StandardizeApiExceptionResponse(
                "2000",
                errors,
                "/errors/validation",
                "Validation Error",
                "/Errors/validation"
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    /**
     * Handler for business rule violations.
     */
    @ExceptionHandler(com.businessdomain.user.exceptions.exceptions.BusinessRuleException.class)
    public ResponseEntity<StandardizedApiExceptionResponse> handleBusinessRuleException(BusinessRuleException ex) {
        StandardizedApiExceptionResponse response = new StandardizedApiExceptionResponse(
                ex.getCode(),
                ex.getMessage(),
                "/errors/business/" + (ex.getId() != null ? ex.getId() : "unknown"),
                "Business Rule Violation",
                "/errors/business"
        );

        return ResponseEntity.status(ex.getHttpStatus()).body(response);
    }

    /**
     * Fallback handler for unhandled exceptions.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardizedApiExceptionResponse> handleGenericException(Exception ex) {
        StandardizedApiExceptionResponse response = new StandardizedApiExceptionResponse(
                "5000",
                "Unexpected error: " + ex.getMessage(),
                "/errors/server/unexpected",
                "Internal Server Error",
                "/errors/server"
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
