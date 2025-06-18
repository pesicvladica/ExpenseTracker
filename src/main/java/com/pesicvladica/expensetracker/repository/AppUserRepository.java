package com.pesicvladica.expensetracker.repository;

import com.pesicvladica.expensetracker.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
