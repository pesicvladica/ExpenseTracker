package com.pesicvladica.expensetracker.repository;

import com.pesicvladica.expensetracker.model.AppUser;
import com.pesicvladica.expensetracker.model.Transaction;
import com.pesicvladica.expensetracker.model.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    boolean existsByIdAndUser(Long id, AppUser user);

    @Query("SELECT t FROM Transaction t JOIN FETCH t.user WHERE t.id = :id")
    Optional<Transaction> findByIdWithUser(@Param("id") Long id);

    Stream<Transaction> findByUserAndTypeOrderByTimeAddedDesc(AppUser user, TransactionType type);

    @Query("SELECT t FROM Transaction t JOIN FETCH t.user " +
            "WHERE t.user = :user AND t.type = :type AND t.timeAdded " +
            "BETWEEN :start AND :end " +
            "ORDER BY t.timeAdded ASC")
    Stream<Transaction> findByUserAndTypeAndMonthWithUser(@Param("user") AppUser user,
                                                        @Param("type") TransactionType type,
                                                        @Param("start") LocalDateTime start,
                                                        @Param("end") LocalDateTime end);
}