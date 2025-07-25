package com.pesicvladica.expensetracker.controller;

import com.pesicvladica.expensetracker.dto.transaction.IncomeCreateRequest;
import com.pesicvladica.expensetracker.dto.transaction.OutcomeCreateRequest;
import com.pesicvladica.expensetracker.dto.transaction.TransactionManagementResponse;
import com.pesicvladica.expensetracker.dto.transaction.TransactionUpdateRequest;
import com.pesicvladica.expensetracker.service.transactions.TransactionService;
import jakarta.transaction.InvalidTransactionException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/transaction")
public class TransactionManagementController {

    // region Properties

    private final TransactionService transactionService;

    // endregion

    // region Initialization

    public TransactionManagementController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // endregion

    // region API Endpoints

    @PostMapping("/income/add")
    public ResponseEntity<TransactionManagementResponse> addIncome(@Valid @RequestBody IncomeCreateRequest request) {
        var createdTransaction = transactionService.addTransaction(request, null);
        var response = new TransactionManagementResponse(createdTransaction);
        return ResponseEntity.created(URI.create("/api/transactions/income/" + response.getId())).body(response);
    }

    @PostMapping("/outcome/add")
    public ResponseEntity<TransactionManagementResponse> addOutcome(@Valid @RequestBody OutcomeCreateRequest request) {
        var createdTransaction = transactionService.addTransaction(request, null);
        var response = new TransactionManagementResponse(createdTransaction);
        return ResponseEntity.created(URI.create("/api/transactions/outcome/" + response.getId())).body(response);
    }

    @PutMapping("/{transactionId}")
    public ResponseEntity<TransactionManagementResponse> updateTransaction(@PathVariable Long transactionId,
                                                                           @Valid @RequestBody TransactionUpdateRequest request) throws InvalidTransactionException {
        transactionService.updateTransaction(transactionId, request, null);

        var updatedTransaction = transactionService
                                    .getTransactionById(transactionId, null)
                                    .orElseThrow(() -> new InvalidTransactionException("Transaction not found after update."));
        var response = new TransactionManagementResponse(updatedTransaction);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        transactionService.deleteTransaction(transactionId, null);
        return ResponseEntity.noContent().build();
    }

    // endregion
}