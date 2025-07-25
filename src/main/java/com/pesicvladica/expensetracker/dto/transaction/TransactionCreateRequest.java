package com.pesicvladica.expensetracker.dto.transaction;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.pesicvladica.expensetracker.model.TransactionType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonSubTypes({
        @JsonSubTypes.Type(value = IncomeCreateRequest.class, name = "INCOME"),
        @JsonSubTypes.Type(value = OutcomeCreateRequest.class, name = "OUTCOME")
})
public abstract class TransactionCreateRequest {

    // region Properties

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private final BigDecimal amount;

    @NotNull(message = "Time added is required")
    private final LocalDateTime timeAdded;

    @NotNull(message = "Transaction type is required")
    private final TransactionType type;

    // endregion

    // region Initialization

    @JsonCreator
    protected TransactionCreateRequest(@JsonProperty("amount")  BigDecimal amount,
                                       @JsonProperty("timeAdded") LocalDateTime timeAdded,
                                       @JsonProperty("type") TransactionType type) {
        this.amount = amount;
        this.timeAdded = timeAdded;
        this.type = type;
    }

    // endregion

    // region Getters

    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimeAdded() { return timeAdded; }
    public TransactionType getType() { return type; }

    // endregion
}

