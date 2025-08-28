package com.pesicvladica.expensetracker.service.transactions;

import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.model.TransactionType;
import com.pesicvladica.expensetracker.repository.TransactionRepository;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import com.pesicvladica.expensetracker.util.logging.TrackTime;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.stream.Stream;

public class TransactionStatisticServiceImpl implements TransactionStatisticService {

    // region Properties

    private final TransactionRepository transactionRepository;

    // endregion

    // region Initialization

    public TransactionStatisticServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // region TransactionService

    // region TransactionStatisticService

    @Override
    @Transactional(readOnly = true)
    @TrackTime
    public Stream<Transaction> getMonthlyTransactionsForType(Integer month,
                                                             TransactionType type,
                                                             @AuthenticationPrincipal AppUserDetails currentUser) {
        var user = currentUser.getAppUser();

        var currentYear = LocalDateTime.now().getYear();
        int lastDayOfMonth = YearMonth.of(currentYear, month).lengthOfMonth();
        LocalDateTime startDate = LocalDateTime.of(currentYear, month, 1, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(currentYear, month, lastDayOfMonth, 0, 0);

        return transactionRepository.findByUserAndTypeAndMonthWithUser(user, type, startDate, endDate);
    }

    // endregion
}
