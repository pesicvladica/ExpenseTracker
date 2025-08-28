package com.pesicvladica.expensetracker.controller;

import com.pesicvladica.expensetracker.dto.transaction.TransactionManagementResponse;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import com.pesicvladica.expensetracker.service.transactions.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/incomes")
    public ResponseEntity<PagedModel<EntityModel<TransactionManagementResponse>>> getIncomes(@AuthenticationPrincipal AppUserDetails currentUser,
                                                                                             PagedResourcesAssembler<TransactionManagementResponse> assembler,
                                                                                             @RequestParam(defaultValue = "0") int pageNo,
                                                                                             @RequestParam(defaultValue = "10") int pageSize) {
        Page<TransactionManagementResponse> transactions = transactionService.getIncomes(currentUser, pageNo, pageSize)
                .map(TransactionManagementResponse::new);

        return ResponseEntity.ok().body(assembler.toModel(transactions));
    }

    @GetMapping("/outcomes")
    public ResponseEntity<PagedModel<EntityModel<TransactionManagementResponse>>> getOutcomes(@AuthenticationPrincipal AppUserDetails currentUser,
                                                                           PagedResourcesAssembler<TransactionManagementResponse> assembler,
                                                                           @RequestParam(defaultValue = "0") int pageNo,
                                                                           @RequestParam(defaultValue = "10") int pageSize) {
        Page<TransactionManagementResponse> transactions = transactionService.getOutcomes(currentUser, pageNo, pageSize)
                .map(TransactionManagementResponse::new);
        return ResponseEntity.ok().body(assembler.toModel(transactions));
    }

    // endregion
}
