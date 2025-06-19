package com.pesicvladica.expensetracker.config;

import com.pesicvladica.expensetracker.repository.AppUserRepository;
import com.pesicvladica.expensetracker.service.authentication.security.AppUserDetailsService;
import com.pesicvladica.expensetracker.util.security.AuthenticationFilter;
import com.pesicvladica.expensetracker.util.security.JsonWebToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity()
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception { return config.getAuthenticationManager(); }

    @Bean
    public AppUserDetailsService appUserDetailsService(AppUserRepository appUserRepository) { return new AppUserDetailsService(appUserRepository); }

    @Bean
    public JsonWebToken jsonWebToken(@Value("${jwt.secret}") String secret) { return new JsonWebToken(secret); }

    @Bean
    public AuthenticationFilter authenticationFilter(JsonWebToken jsonWebToken, AppUserDetailsService appUserDetailsService) { return new AuthenticationFilter(jsonWebToken, appUserDetailsService); }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   AuthenticationFilter authenticationFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/api/user/**").authenticated()
                )
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
