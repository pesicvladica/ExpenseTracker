package com.pesicvladica.expensetracker.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    // region Columns

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BIGINT UNSIGNED")
    private final  AppUser user;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "time_added", nullable = false)
    private LocalDateTime timeAdded;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private final TransactionType type;

    // endregion

    // region Initialization

    protected Transaction() {
        this.user = null;
        this.amount = null;
        this.timeAdded = null;
        this.type = null;
    }

    private Transaction(AppUser user, BigDecimal amount, LocalDateTime timeAdded, TransactionType type) {
        this.user = user;
        this.amount = amount;
        this.timeAdded = timeAdded;
        this.type = type;
    }

    public static Transaction createIncome(AppUser user, BigDecimal amount, LocalDateTime timeAdded) {
        return new Transaction(user, amount, timeAdded, TransactionType.INCOME);
    }

    public static Transaction createOutcome(AppUser user, BigDecimal amount, LocalDateTime timeAdded) {
        return new Transaction(user, amount, timeAdded, TransactionType.OUTCOME);
    }

    // endregion

    // region Getters/Setters

    public Long getId() { return id; }
    public AppUser getUser() { return user; }
    public BigDecimal getAmount() { return amount; }
    public void setTimeAdded(LocalDateTime timeAdded) { this.timeAdded = timeAdded; }
    public LocalDateTime getTimeAdded() { return timeAdded; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public TransactionType getType() { return type; }

    // endregion

}
