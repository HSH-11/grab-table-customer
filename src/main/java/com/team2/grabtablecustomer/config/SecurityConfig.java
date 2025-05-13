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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/index.html",
                                "/csrf-token",
                                "/login", "/login.html",
                                "/register", "/register.html",
                                "/css/**", "/js/**", "/images/**", "/assets/**", "/fonts/**"
                        ).permitAll()
                        .requestMatchers("/api/gold/**").hasRole("GOLD")
                        .requestMatchers("/api/silver/**").hasAnyRole("GOLD", "SILVER")
                        .requestMatchers("/api/bronze/**").hasAnyRole("GOLD", "SILVER", "BRONZE")
                        .anyRequest().authenticated()
                )
//                .csrf(csrf -> csrf.disable())
          .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .formLogin(form -> form
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
