package com.team2.grabtablecustomer.common.controller;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DebugController {

    /** login 후 세션에 잘 담겨있는지 확인용 임시 api */
    @GetMapping("/checkLogin")
    public ResponseEntity<?> getLoggedInUserInfo(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).body("로그인되지 않은 사용자입니다.");
        }

        CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();

        Map<String, Object> result = new HashMap<>();
        result.put("email", userDetails.getEmail());
        result.put("authorities", userDetails.getAuthorities());

        return ResponseEntity.ok(result);
    }
}
