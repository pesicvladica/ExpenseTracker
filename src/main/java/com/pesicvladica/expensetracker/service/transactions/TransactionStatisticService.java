package com.pesicvladica.expensetracker.service.transactions;

import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.model.TransactionType;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.stream.Stream;

public interface TransactionStatisticService {

    Stream<Transaction> getMonthlyTransactionsForType(Integer month, TransactionType type, @AuthenticationPrincipal AppUserDetails currentUser);
}
