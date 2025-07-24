package com.pesicvladica.expensetracker.service.transactions;

import com.pesicvladica.expensetracker.dto.transaction.TransactionCreateRequest;
import com.pesicvladica.expensetracker.dto.transaction.TransactionUpdateRequest;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Optional;

@PreAuthorize("isAuthenticated()")
public interface TransactionService {
    Transaction addTransaction(TransactionCreateRequest data, @AuthenticationPrincipal AppUserDetails currentUser);
    @PreAuthorize("@securityService.isTransactionOwner(#transactionId, #currentUser.id)")
    void updateTransaction(Long transactionId, TransactionUpdateRequest data, @AuthenticationPrincipal AppUserDetails currentUser);
    @PreAuthorize("@securityService.isTransactionOwner(#transactionId, #currentUser.id)")
    void deleteTransaction(Long transactionId, @AuthenticationPrincipal AppUserDetails currentUser);
    @PreAuthorize("@securityService.isTransactionOwner(#transactionId, #currentUser.id)")
    Optional<Transaction> getTransactionById(Long transactionId, @AuthenticationPrincipal AppUserDetails currentUser);
    List<Transaction> getTransactions(@AuthenticationPrincipal AppUserDetails currentUser);
}