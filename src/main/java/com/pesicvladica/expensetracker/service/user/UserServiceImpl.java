package com.pesicvladica.expensetracker.service.user;

import com.pesicvladica.expensetracker.dto.user.UserResponse;
import com.pesicvladica.expensetracker.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    // region Properties

    @Autowired
    private AppUserRepository appUserRepository;

    // endregion

    // region UserService

    @Override
    public UserResponse getUser(String username) {
        var user = appUserRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User could not be located")
                );
        return new UserResponse(user);
    }

    // endregion
}
