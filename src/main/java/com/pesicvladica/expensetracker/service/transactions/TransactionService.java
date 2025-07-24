package com.pesicvladica.expensetracker.service.transactions;

import com.pesicvladica.expensetracker.dto.transaction.TransactionCreateRequest;
import com.pesicvladica.expensetracker.dto.transaction.TransactionUpdateRequest;
import com.pesicvladica.expensetracker.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    void addTransaction(TransactionCreateRequest data);
    void updateTransaction(Long transactionId, TransactionUpdateRequest data);
    void deleteTransaction(Long transactionId);

    Optional<Transaction> getTransactionById(Long transactionId);
    List<Transaction> getTransactions();
}