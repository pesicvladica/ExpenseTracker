package com.pesicvladica.expensetracker.controller;

import com.pesicvladica.expensetracker.dto.transaction.TransactionManagementResponse;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import com.pesicvladica.expensetracker.service.transactions.TransactionService;
import jakarta.transaction.InvalidTransactionException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionOverviewController {

    // region Properties

    private final TransactionService transactionService;

    // endregion

    // region Initialization

    public TransactionOverviewController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // endregion

    // region API Endpoints

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionManagementResponse> getTransactionById(@PathVariable Long transactionId) throws InvalidTransactionException {
        Optional<Transaction> transactionOptional = transactionService.getTransactionById(transactionId);
        transactionOptional.ifPresent(transaction -> transaction.getUser().getUsername());
        return transactionOptional
                .map(transaction -> ResponseEntity.ok().body(new TransactionManagementResponse(transaction)))
                .orElseThrow(() -> new InvalidTransactionException("Transaction with ID " + transactionId + " not found."));
    }

    @GetMapping("/incomes")
    public ResponseEntity<List<TransactionManagementResponse>> getIncomes(@AuthenticationPrincipal AppUserDetails currentUser) {
        List<TransactionManagementResponse> transactions = transactionService.getIncomes(currentUser).stream()
                .map(TransactionManagementResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(transactions);
    }

    @GetMapping("/outcomes")
    public ResponseEntity<List<TransactionManagementResponse>> getOutcomes(@AuthenticationPrincipal AppUserDetails currentUser) {
        List<TransactionManagementResponse> transactions = transactionService.getOutcomes(currentUser).stream()
                .map(TransactionManagementResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(transactions);
    }

    // endregion
}
