package com.pesicvladica.expensetracker.service.transactions;

import com.pesicvladica.expensetracker.dto.transaction.TransactionCreateRequest;
import com.pesicvladica.expensetracker.dto.transaction.TransactionUpdateRequest;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.Optional;

public interface TransactionService {

    Transaction addTransaction(TransactionCreateRequest data, @AuthenticationPrincipal AppUserDetails currentUser);
    Transaction updateTransaction(Long transactionId, TransactionUpdateRequest data);
    void deleteTransaction(Long transactionId);
    Optional<Transaction> getTransactionById(Long transactionId);

    Page<Transaction> getIncomes(@AuthenticationPrincipal AppUserDetails currentUser, int pageNo, int pageSize);
    Page<Transaction> getOutcomes(@AuthenticationPrincipal AppUserDetails currentUser, int pageNo, int pageSize);
}