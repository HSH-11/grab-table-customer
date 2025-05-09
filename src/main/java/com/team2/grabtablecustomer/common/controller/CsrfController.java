package com.team2.grabtablecustomer.common.controller;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** login.html window.onload event에서 이 token 값을 요청 */
@RestController
public class CsrfController {   // json으로 토큰 받음

    @GetMapping("/csrf-token")
    public CsrfToken csrf(CsrfToken token) {
        return token;
    }
}
