package com.pesicvladica.expensetracker.service.user;

import com.pesicvladica.expensetracker.dto.UserResponse;

public interface UserService {
    UserResponse getUser(String username);
}
