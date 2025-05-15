package com.team2.grabtablecustomer.config;

import jakarta.servlet.http.HttpServletResponse;
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
                                "/category","/category.html",
                                "/store-detail","/store-detail.html",
                                "/register", "/register.html",
                                "/css/**", "/js/**", "/images/**", "/assets/**", "/fonts/**",
                                "/debug/**",     // 디버깅, 테스팅용 임시 오픈
                                "/api/menus/**",
                                "/api/stores/**",
                                "/api/reviews/menus/**"
                        ).permitAll()
                        .requestMatchers("/api/gold/**").hasRole("GOLD")
                        .requestMatchers("/api/silver/**").hasAnyRole("GOLD", "SILVER")
                        .requestMatchers("/api/bronze/**").hasAnyRole("GOLD", "SILVER", "BRONZE")
                        .anyRequest().authenticated()
                )
                // .csrf(csrf -> csrf.disable())
                .csrf(csrf -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
                        .successHandler(successHandler)
                        .failureHandler(failureHandler)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll())
                // .logout(logout -> logout
                //         .logoutUrl("/logout")
                //         .logoutSuccessUrl("/index.html")    // 로그아웃 후 리다이렉트
                //         .deleteCookies("JSESSIONID")        // 세션 쿠키 삭제
                //         .invalidateHttpSession(true)        // 세션 무효화
                //         .clearAuthentication(true)          // SecurityContext 비우기
                //         .permitAll()
                // )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        }))
                .build();
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
