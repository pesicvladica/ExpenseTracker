package com.pesicvladica.expensetracker.service.transactions;

import com.pesicvladica.expensetracker.dto.transaction.TransactionCreateRequest;
import com.pesicvladica.expensetracker.dto.transaction.TransactionUpdateRequest;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Optional;
import java.util.stream.Stream;

public interface TransactionService {

    Transaction addTransaction(TransactionCreateRequest data, @AuthenticationPrincipal AppUserDetails currentUser);
    Transaction updateTransaction(Long transactionId, TransactionUpdateRequest data);
    void deleteTransaction(Long transactionId);
    Optional<Transaction> getTransactionById(Long transactionId);

    Stream<Transaction> getIncomes(@AuthenticationPrincipal AppUserDetails currentUser);
    Stream<Transaction> getOutcomes(@AuthenticationPrincipal AppUserDetails currentUser);
}