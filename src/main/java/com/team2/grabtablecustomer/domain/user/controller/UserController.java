package com.team2.grabtablecustomer.domain.user.controller;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.domain.user.dto.UserDto;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResultDto> insertUser(UserDto userDto) {
        UserResultDto userResultDto = userService.insertUser(userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResultDto);
    }

    /** 로그인된 사용자를 세션에서 불러와서 getEmail 한 후, DB에서 해당 사용자 정보 불러오기 */
    @GetMapping
    public ResponseEntity<UserResultDto> getLoggedInUser(Authentication authentication) {
        CustomerUserDetails userDetails = (CustomerUserDetails) authentication.getPrincipal();
        String email = userDetails.getEmail();

        UserResultDto resultDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(resultDto);
    }


}
