package com.pesicvladica.expensetracker.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // region Exception handling

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<AppError> handleUserExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(new AppError(ex.getMessage()));
    }

    @ExceptionHandler(CredentialsInvalidException.class)
    public ResponseEntity<AppError> handleCredentialsInvalidException(CredentialsInvalidException ex) {
        return ResponseEntity.badRequest().body(new AppError(ex.getMessage()));
    }

    // endregion
}
