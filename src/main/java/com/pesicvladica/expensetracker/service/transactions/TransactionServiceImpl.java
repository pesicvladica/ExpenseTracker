package com.pesicvladica.expensetracker.service.transactions;

import com.pesicvladica.expensetracker.dto.transaction.TransactionCreateRequest;
import com.pesicvladica.expensetracker.dto.transaction.TransactionUpdateRequest;
import com.pesicvladica.expensetracker.exception.TransactionInvalidException;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.model.TransactionType;
import com.pesicvladica.expensetracker.repository.TransactionRepository;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import com.pesicvladica.expensetracker.util.validator.TransactionCreateRequestValidator;
import com.pesicvladica.expensetracker.util.validator.TransactionUpdateRequestValidator;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Stream;

@Service
@PreAuthorize("isAuthenticated()")
public class TransactionServiceImpl implements TransactionService {

    // region Properties

    private final TransactionRepository transactionRepository;
    private final TransactionCreateRequestValidator transactionCreateRequestValidator;
    private final TransactionUpdateRequestValidator transactionUpdateRequestValidator;

    // endregion

    // region Initialization

    public TransactionServiceImpl(TransactionRepository transactionRepository,
                                  TransactionCreateRequestValidator transactionCreateRequestValidator,
                                  TransactionUpdateRequestValidator transactionUpdateRequestValidator) {
        this.transactionRepository = transactionRepository;
        this.transactionCreateRequestValidator = transactionCreateRequestValidator;
        this.transactionUpdateRequestValidator = transactionUpdateRequestValidator;
    }

    // region TransactionService

    @Override
    @Transactional
    public Transaction addTransaction(TransactionCreateRequest data, @AuthenticationPrincipal AppUserDetails currentUser) {
        transactionCreateRequestValidator.validate(data);
        var user = currentUser.getAppUser();
        Transaction newTransaction = Transaction.createTransaction(user, data.getAmount(), data.getTimeAdded(), data.getType());
        return transactionRepository.save(newTransaction);
    }

    @Override
    @Transactional
    @PreAuthorize("@securityService.isTransactionOwner(#transactionId)")
    public Transaction updateTransaction(Long transactionId, TransactionUpdateRequest data) {
        transactionUpdateRequestValidator.validate(data);
        var transaction = transactionRepository.findById(transactionId).orElse(null);
        if (transaction == null) {
            throw new TransactionInvalidException("Could not locate transaction!");
        }
        if (data.getAmount() != null) {
            transaction.setAmount(data.getAmount());
        }
        if (data.getTimeAdded() != null) {
            transaction.setTimeAdded(data.getTimeAdded());
        }

        try {
            return transactionRepository.save(transaction);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw e;
        }
    }

    @Override
    @Transactional
    @PreAuthorize("@securityService.isTransactionOwner(#transactionId)")
    public void deleteTransaction(Long transactionId) {
        try {
            transactionRepository.deleteById(transactionId);
        } catch (EmptyResultDataAccessException ex) {
            throw ex;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("@securityService.isTransactionOwner(#transactionId)")
    public Optional<Transaction> getTransactionById(Long transactionId) {
        return transactionRepository.findByIdWithUser(transactionId);
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<Transaction> getIncomes(@AuthenticationPrincipal AppUserDetails currentUser) {
        var user = currentUser.getAppUser();
        return transactionRepository.findByUserAndTypeOrderByTimeAddedDesc(user, TransactionType.INCOME);
    }

    @Override
    @Transactional(readOnly = true)
    public Stream<Transaction> getOutcomes(@AuthenticationPrincipal AppUserDetails currentUser) {
        var user = currentUser.getAppUser();
        return transactionRepository.findByUserAndTypeOrderByTimeAddedDesc(user, TransactionType.OUTCOME);
    }

    // endregion
}