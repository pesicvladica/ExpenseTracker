package com.pesicvladica.expensetracker.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // region Exception handling

    @ExceptionHandler(BlockedUserException.class)
    public ResponseEntity<AppError> handleBlockedUserExceptionException(BlockedUserException ex) {
        return ResponseEntity.badRequest().body(new AppError(ex.getMessage()));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<AppError> handleUserExistsException(UserAlreadyExistsException ex) {
        return ResponseEntity.badRequest().body(new AppError(ex.getMessage()));
    }

    @ExceptionHandler(CredentialsInvalidException.class)
    public ResponseEntity<AppError> handleCredentialsInvalidException(CredentialsInvalidException ex) {
        return ResponseEntity.badRequest().body(new AppError(ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<AppError> handleUserNotFoundException(UsernameNotFoundException ex) {
        return ResponseEntity.badRequest().body(new AppError(ex.getMessage()));
    }

    @ExceptionHandler(TransactionInvalidException.class)
    public ResponseEntity<AppError> handleTransactionNotFound(TransactionInvalidException ex) {
        return ResponseEntity.badRequest().body(new AppError(ex.getMessage()));
    }

    // endregion
}
