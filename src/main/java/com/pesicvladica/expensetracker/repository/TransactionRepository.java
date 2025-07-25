package com.pesicvladica.expensetracker.repository;

import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.stream.Stream;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByIdAndUser(Long id, AppUser user);
    Stream<Transaction> findByAppUserAndTypeOrderByTimeAddedDesc(AppUser user, TransactionType type);
}