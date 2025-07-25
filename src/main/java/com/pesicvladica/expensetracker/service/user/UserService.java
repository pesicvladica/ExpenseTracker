package com.pesicvladica.expensetracker.service.user;

import com.pesicvladica.expensetracker.model.AppUser;

public interface UserService {
    AppUser getUser(String username);
}
