package com.api.MedSync.shared.exceptions;

import jakarta.validation.ConstraintViolation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Set;
import java.util.stream.Collectors;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GlobalExceptionHandler extends RuntimeException {

    public GlobalExceptionHandler(String resourceName, String message) {
        super(resourceName + ": " + message);
    }

    public <T> GlobalExceptionHandler(String resourceName, Set<ConstraintViolation<T>> violations) {
        super(violations.stream()
                .map(violation -> String.format("%s : %s", violation.getPropertyPath(), violation.getMessage()))
                .collect(Collectors.joining(", ") ) );
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return null;
    }
}
