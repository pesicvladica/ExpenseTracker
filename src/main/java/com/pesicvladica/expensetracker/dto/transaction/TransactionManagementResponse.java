package com.pesicvladica.expensetracker.dto.transaction;

import com.pesicvladica.expensetracker.model.Transaction;

public class TransactionManagementResponse {

    // region Properties

    private final Long id;

    // endregion

    // region Initialization

    public TransactionManagementResponse(Transaction transaction) {
        this.id = transaction.getId();
    }

    // endregion

    // region Getters

    public Long getId() { return id; }

    // endregion
}
