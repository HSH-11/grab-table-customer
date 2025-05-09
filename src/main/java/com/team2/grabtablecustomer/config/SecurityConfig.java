package com.team2.grabtablecustomer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(
            HttpSecurity http,
            MyAuthenticationSuccessHandler successHandler,
            MyAuthenticationFailureHandler failureHandler
    ) throws Exception {
        return http
                .authorizeHttpRequests(
                        request -> {
                            request.requestMatchers(
                                    "/", "/index.html",
                                    "/csrf-token",
                                    "/login", "/login.html",
                                    "/register", "/register.html",
                                    "/css/**", "/js/**", "/images/**", "/fonts/**",
                                    "/api/**"   // 임시로 모든 api 접근 허용
                            ).permitAll()
                            .anyRequest().authenticated();
                        }
                )
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .formLogin(form
                        -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
