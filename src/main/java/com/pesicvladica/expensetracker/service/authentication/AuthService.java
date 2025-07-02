package com.pesicvladica.expensetracker.service.authentication;

import com.pesicvladica.expensetracker.dto.DeviceInfo;
import com.pesicvladica.expensetracker.dto.UserLoginRequest;
import com.pesicvladica.expensetracker.dto.UserRegisterRequest;
import com.pesicvladica.expensetracker.dto.UserAuthResponse;
import com.pesicvladica.expensetracker.model.AppUser;

public interface AuthService {
    UserAuthResponse register(UserRegisterRequest request, DeviceInfo deviceInfo);
    UserAuthResponse login(UserLoginRequest request, DeviceInfo deviceInfo);
    void logout(AppUser user);
}
