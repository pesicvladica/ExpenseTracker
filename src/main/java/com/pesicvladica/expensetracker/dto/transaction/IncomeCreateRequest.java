package com.pesicvladica.expensetracker.dto.transaction;

import com.pesicvladica.expensetracker.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class IncomeCreateRequest extends TransactionCreateRequest {

    public IncomeCreateRequest() {
        super();
        setType(TransactionType.INCOME);
    }

    public IncomeCreateRequest(BigDecimal amount, LocalDateTime timeAdded) {
        super(amount, timeAdded, TransactionType.INCOME);
    }
}
