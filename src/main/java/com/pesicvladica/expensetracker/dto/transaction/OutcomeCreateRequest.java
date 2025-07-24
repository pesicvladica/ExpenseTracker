package com.pesicvladica.expensetracker.dto.transaction;

import com.pesicvladica.expensetracker.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class OutcomeCreateRequest extends TransactionCreateRequest {

    public OutcomeCreateRequest() {
        super();
        setType(TransactionType.OUTCOME);
    }

    public OutcomeCreateRequest(BigDecimal amount, LocalDateTime timeAdded) {
        super(amount, timeAdded, TransactionType.OUTCOME);
    }
}
