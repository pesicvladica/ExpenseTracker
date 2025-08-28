package com.pesicvladica.expensetracker.dto.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
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

    @JsonCreator
    public TransactionUpdateRequest(@JsonProperty("amount") @Nullable BigDecimal amount,
                                    @JsonProperty("timeAdded") @Nullable LocalDateTime timeAdded) {
        this.amount = amount;
        this.timeAdded = timeAdded;
    }

    // endregion

    // region Getters

    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimeAdded() { return timeAdded; }

    // endregion
}
