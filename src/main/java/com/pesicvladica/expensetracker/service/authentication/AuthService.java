package com.pesicvladica.expensetracker.service.authentication;

import com.pesicvladica.expensetracker.dto.UserLoginRequest;
import com.pesicvladica.expensetracker.dto.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.UserAuthResponse;

public interface AuthService {
    UserAuthResponse register(UserRegisterRequest request);
    UserAuthResponse login(UserLoginRequest request);
}
