package com.pesicvladica.expensetracker.util.validator;

import com.pesicvladica.expensetracker.dto.transaction.TransactionCreateRequest;
import com.pesicvladica.expensetracker.exception.TransactionInvalidException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransactionCreateRequestValidator implements Validator<TransactionCreateRequest> {

    // region Validator

    @Override
    public void validate(TransactionCreateRequest obj) {
        if (obj.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionInvalidException("Transaction amount must be positive!");
        }
        if (obj.getTimeAdded() == null && !obj.getTimeAdded().isAfter(LocalDateTime.now())) {
            throw new TransactionInvalidException("Transaction time must be provided and not in future!");
        }
        if (obj.getType() == null) {
            throw new TransactionInvalidException("Transaction type must be provided!");
        }
    }

    // endregion
}
