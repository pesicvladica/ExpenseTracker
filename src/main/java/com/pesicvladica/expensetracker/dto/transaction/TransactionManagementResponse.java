package com.pesicvladica.expensetracker.dto.transaction;

import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionManagementResponse {

    // region Properties

    private final Long id;
    private final AppUser user;
    private final BigDecimal amount;
    private final LocalDateTime timeAdded;
    private final TransactionType type;

    // endregion

    // region Initialization

    public TransactionManagementResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.user = transaction.getUser();
        this.amount = transaction.getAmount();
        this.timeAdded = transaction.getTimeAdded();
        this.type = transaction.getType();
    }

    // endregion

    // region Getters

    public Long getId() { return id; }
    public AppUser getUser() { return user; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimeAdded() { return timeAdded; }
    public TransactionType getType() { return type; }

    // endregion
}
