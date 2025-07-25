package com.pesicvladica.expensetracker.service.authentication;

import com.pesicvladica.expensetracker.dto.DeviceInfo;
import com.pesicvladica.expensetracker.dto.user.UserLoginRequest;
import com.pesicvladica.expensetracker.dto.user.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.user.UserAuthResponse;
import com.pesicvladica.expensetracker.model.AppUser;

public interface AuthService {
    AppUser register(UserRegisterRequest request, DeviceInfo deviceInfo);
    AppUser login(UserLoginRequest request, DeviceInfo deviceInfo);
    void logout(AppUser user);
}
