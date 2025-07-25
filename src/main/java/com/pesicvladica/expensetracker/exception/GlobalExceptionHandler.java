package com.pesicvladica.expensetracker.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleAccessDenied(AccessDeniedException ex) {
        AppError apiError = new AppError("Access Denied: " + ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAllExceptions(Exception ex) {
        AppError apiError = new AppError("Unknown error: " + ex.getMessage());
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // endregion

    // region ResponseEntityExceptionHandler

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {

        AppError apiError = new AppError("Input validation failed");
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    // endregion
}
