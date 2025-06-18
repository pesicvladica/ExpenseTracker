package com.pesicvladica.expensetracker.service;

import com.pesicvladica.expensetracker.dto.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.UserResponse;

public interface AuthService {
    UserResponse register(UserRegisterRequest request);
}
