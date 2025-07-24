package com.pesicvladica.expensetracker.exception;

public class TransactionInvalidException extends RuntimeException {
    public TransactionInvalidException(String message) {
        super(message);
    }
}
