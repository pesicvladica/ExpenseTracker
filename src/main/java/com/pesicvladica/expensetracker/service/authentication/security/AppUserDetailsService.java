package com.pesicvladica.expensetracker.service.authentication.security;

import com.pesicvladica.expensetracker.repository.AppUserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    // region Properties

    private final AppUserRepository appUserRepository;

    // endregion

    // region Initialization

    public AppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // endregion

    // region UserDetailsService

    @Override
    public AppUserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        var appUser = appUserRepository
                .findByUsername(usernameOrEmail)
                .orElseGet(() ->
                        appUserRepository
                                .findByEmail(usernameOrEmail)
                                .orElseThrow(() ->
                                        new UsernameNotFoundException("Username or email not found!")
                                )
                );
        return new AppUserDetails(appUser);
    }

    // endregion
}
