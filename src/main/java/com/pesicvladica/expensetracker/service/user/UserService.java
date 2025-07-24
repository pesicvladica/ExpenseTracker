package com.pesicvladica.expensetracker.service.user;

import com.pesicvladica.expensetracker.dto.user.UserResponse;

public interface UserService {
    UserResponse getUser(String username);
}
