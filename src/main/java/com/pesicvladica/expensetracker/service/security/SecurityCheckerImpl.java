package com.pesicvladica.expensetracker.service.security;

import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service("securityService")
public class SecurityCheckerImpl implements SecurityChecker {

    // region Properties

    private final TransactionRepository transactionRepository;

    // endregion

    // region Initialization

    public SecurityCheckerImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // endregion

    // region SecurityChecker

    @Override
    @Transactional(readOnly = true)
    public boolean isTransactionOwner(Long transactionId, AppUser currentUser) {
        return  transactionRepository.existsByIdAndUser(transactionId, currentUser);
    }

    // endregion
}
