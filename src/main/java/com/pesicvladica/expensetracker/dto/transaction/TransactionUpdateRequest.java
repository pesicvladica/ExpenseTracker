package com.pesicvladica.expensetracker.dto.transaction;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionUpdateRequest {

    // region Properties

    @Positive(message = "Amount must be positive")
    private final BigDecimal amount;

    private final LocalDateTime timeAdded;

    // endregion

    // region Initialization

    protected TransactionUpdateRequest(BigDecimal amount, LocalDateTime timeAdded) {
        this.amount = amount;
        this.timeAdded = timeAdded;
    }

    // endregion

    // region Getters

    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimeAdded() { return timeAdded; }

    // endregion
}
