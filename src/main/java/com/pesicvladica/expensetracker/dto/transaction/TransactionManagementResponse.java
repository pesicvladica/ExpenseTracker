package com.pesicvladica.expensetracker.dto.transaction;

import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionManagementResponse {

    // region Properties

    private final Long id;
    private final Long userId;
    private final String username;
    private final BigDecimal amount;
    private final LocalDateTime timeAdded;
    private final TransactionType type;

    // endregion

    // region Initialization

    public TransactionManagementResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.timeAdded = transaction.getTimeAdded();
        this.type = transaction.getType();

        if (transaction.getUser() != null) {
            this.userId = transaction.getUser().getId();
            this.username = transaction.getUser().getUsername();
        } else {
            this.userId = null;
            this.username = null;
        }
    }

    // endregion

    // region Getters

    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimeAdded() { return timeAdded; }
    public TransactionType getType() { return type; }

    // endregion
}
