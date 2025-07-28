package com.pesicvladica.expensetracker.service.security;

import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.repository.TransactionRepository;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public boolean isTransactionOwner(Long transactionId) {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return false;
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof AppUserDetails currentUserDetails)) {
            return false;
        }
        AppUser currentUser = currentUserDetails.getAppUser();
        if (currentUser == null) {
            return false;
        }

        return  transactionRepository.existsByIdAndUser(transactionId, currentUser);
    }

    // endregion
}
