package com.pesicvladica.expensetracker.util.validator;

import com.pesicvladica.expensetracker.dto.transaction.TransactionUpdateRequest;
import com.pesicvladica.expensetracker.exception.TransactionInvalidException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransactionUpdateRequestValidator implements Validator<TransactionUpdateRequest> {

    // region Validator

    @Override
    public void validate(TransactionUpdateRequest obj) {
        if (obj.getAmount() != null && obj.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionInvalidException("Transaction amount must be positive!");
        }
        if (obj.getTimeAdded() == null && !obj.getTimeAdded().isAfter(LocalDateTime.now())) {
            throw new TransactionInvalidException("Transaction time must be provided and not in future!");
        }
    }

    // endregion
}
