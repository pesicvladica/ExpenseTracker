package com.pesicvladica.expensetracker.controller;

import com.pesicvladica.expensetracker.dto.transaction.IncomeCreateRequest;
import com.pesicvladica.expensetracker.dto.transaction.OutcomeCreateRequest;
import com.pesicvladica.expensetracker.dto.transaction.TransactionManagementResponse;
import com.pesicvladica.expensetracker.dto.transaction.TransactionUpdateRequest;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.service.transactions.TransactionService;
import jakarta.transaction.InvalidTransactionException;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
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

    // TODO: Separate overview from management

    @GetMapping("/{transactionId}")
    public ResponseEntity<TransactionManagementResponse> getTransactionById(@PathVariable Long transactionId) throws InvalidTransactionException {
        Optional<Transaction> transactionOptional = transactionService.getTransactionById(transactionId, null);
        return transactionOptional
                .map(transaction -> ResponseEntity.ok().body(new TransactionManagementResponse(transaction)))
                .orElseThrow(() -> new InvalidTransactionException("Transaction with ID " + transactionId + " not found."));
    }

    @GetMapping("/income")
    public ResponseEntity<List<TransactionManagementResponse>> getIncomes() {
        List<Transaction> transactions = transactionService.getIncomes(null);

        List<TransactionManagementResponse> responseList = transactions.stream()
                .map(TransactionManagementResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseList);
    }

    @GetMapping("/outcome")
    public ResponseEntity<List<TransactionManagementResponse>> getOutcomes() {
        List<Transaction> transactions = transactionService.getOutcomes(null);

        List<TransactionManagementResponse> responseList = transactions.stream()
                .map(TransactionManagementResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(responseList);
    }

    // endregion
}