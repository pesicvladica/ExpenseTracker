package com.pesicvladica.expensetracker.service.security;

public interface SecurityChecker {
    boolean isTransactionOwner(Long transactionId, Long principalId);
}
