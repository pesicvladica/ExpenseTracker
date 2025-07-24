package com.pesicvladica.expensetracker.dto.transaction;

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
    private BigDecimal amount;

    @NotNull(message = "Time added is required")
    private LocalDateTime timeAdded;

    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    // endregion

    // region Initialization

    protected TransactionCreateRequest() {
        this.amount = null;
        this.timeAdded = null;
        this.type = null;
    }

    protected TransactionCreateRequest(BigDecimal amount, LocalDateTime timeAdded, TransactionType type) {
        this.amount = amount;
        this.timeAdded = timeAdded;
        this.type = type;
    }

    // endregion

    // region Getters

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public LocalDateTime getTimeAdded() { return timeAdded; }
    public void setTimeAdded(LocalDateTime timeAdded) { this.timeAdded = timeAdded; }
    public TransactionType getType() { return type; }
    public void setType(TransactionType type) { this.type = type; }

    // endregion
}

