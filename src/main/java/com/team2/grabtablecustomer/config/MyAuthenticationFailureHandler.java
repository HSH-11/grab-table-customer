package com.team2.grabtablecustomer.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException exception
    ) throws IOException, ServletException {
        /** ajax 로그인 성공 결과 리턴 */
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);    // 401
        response.setContentType("application/json");

        String jsonStr = """
                {"result":"fail"}
                """;

        response.getWriter().write(jsonStr);
    }
}
