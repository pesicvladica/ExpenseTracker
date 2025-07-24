package com.pesicvladica.expensetracker.util.validator;

import com.pesicvladica.expensetracker.dto.transaction.TransactionUpdateRequest;
import com.pesicvladica.expensetracker.exception.TransactionInvalidException;

import java.math.BigDecimal;

public class TransactionUpdateRequestValidator implements Validator<TransactionUpdateRequest> {

    // region Validator

    @Override
    public void validate(TransactionUpdateRequest obj) {
        if (obj.getAmount() != null && obj.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionInvalidException("Transaction amount must be positive.");
        }
    }

    // endregion
}
