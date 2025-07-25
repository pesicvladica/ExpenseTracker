package com.pesicvladica.expensetracker.service.security;

import com.pesicvladica.expensetracker.model.AppUser;

public interface SecurityChecker {
    boolean isTransactionOwner(Long transactionId, AppUser currentUser);
}
