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
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    public enum Role {
        USER,
        ADMIN
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "token_version", nullable = false)
    private Long tokenVersion = 0L;

    // endregion

    // region Initialization

    protected AppUser() {
        this.username = null;
        this.email = null;
        this.password = null;
        this.role = null;
    }

    private AppUser(Long id, String username, String email, String password, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
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

    public static AppUser regularExistingUser(Long id, String username, String email) {
        return new AppUser(id, username, email, null, Role.USER);
    }

    // endregion

    // region Getters/Setters

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    @JsonIgnore public String getPassword() { return password; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getTokenVersion() { return tokenVersion; }
    public void incrementTokenVersion() { tokenVersion++; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(Role role) { this.role = role; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setTokenVersion(Long tokenVersion) { this.tokenVersion = tokenVersion; }

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
