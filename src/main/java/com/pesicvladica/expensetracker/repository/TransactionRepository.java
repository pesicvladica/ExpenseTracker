package com.pesicvladica.expensetracker.repository;

import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByAppUserAndTypeOrderByTimeAddedDesc(AppUser user, TransactionType type);
    List<Transaction> findByAppUserAndTypeAndTimeAddedBetween(AppUser user, TransactionType type, LocalDateTime start, LocalDateTime end);
}
