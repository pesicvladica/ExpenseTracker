package com.pesicvladica.expensetracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
public class AppUser {

    // region Columns

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private final String username;

    @Column(nullable = false, unique = true, length = 100)
    private final String email;

    @Column(nullable = false)
    private final String password;

    public enum Role {
        USER,
        ADMIN
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private final Role role;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    // endregion

    // region Initialization

    protected AppUser() {
        this.username = null;
        this.email = null;
        this.password = null;
        this.role = null;
    }

    private AppUser(String username, String email, String password, Role role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static AppUser regularUser(String username, String email, String password) {
        return new AppUser(username, email, password, Role.USER);
    }

    // endregion

    // region Getters/Setters

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    @JsonIgnore public String getPassword() { return password; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // endregion

    // region Comparison

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id) &&
                Objects.equals(username, appUser.username) &&
                Objects.equals(email, appUser.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }

    // endregion
}
