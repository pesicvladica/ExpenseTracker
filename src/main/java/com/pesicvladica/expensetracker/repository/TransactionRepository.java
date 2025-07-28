package com.pesicvladica.expensetracker.repository;

import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByIdAndUser(Long id, AppUser user);

    @Query("SELECT t FROM Transaction t JOIN FETCH t.user WHERE t.id = :id")
    Optional<Transaction> findByIdWithUser(@Param("id") Long id);

    List<Transaction> findByUserAndTypeOrderByTimeAddedDesc(AppUser user, TransactionType type);
}