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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
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
    public void updateTransaction(Long transactionId, TransactionUpdateRequest data, @AuthenticationPrincipal AppUserDetails currentUser) {
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
        transactionRepository.save(transaction);
    }

    @Override
    @Transactional
    public void deleteTransaction(Long transactionId, @AuthenticationPrincipal AppUserDetails currentUser) {
        transactionRepository.deleteById(transactionId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Transaction> getTransactionById(Long transactionId, @AuthenticationPrincipal AppUserDetails currentUser) {
        return transactionRepository.findById(transactionId);

    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getIncomes(@AuthenticationPrincipal AppUserDetails currentUser) {
        var user = currentUser.getAppUser();
        return transactionRepository.findByAppUserAndTypeOrderByTimeAddedDesc(user, TransactionType.INCOME).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Transaction> getOutcomes(@AuthenticationPrincipal AppUserDetails currentUser) {
        var user = currentUser.getAppUser();
        return transactionRepository.findByAppUserAndTypeOrderByTimeAddedDesc(user, TransactionType.OUTCOME).toList();
    }

    // endregion
}