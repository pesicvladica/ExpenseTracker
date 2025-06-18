package com.pesicvladica.expensetracker.exception;

public class AppError {

    // region Properties

    private final String message;

    // endregion

    // region Initialization

    public AppError(String message) {
        this.message = message;
    }

    // endregion

    // region Getters

    public String getMessage() { return message; }

    // endregion
}
