package com.team2.grabtablecustomer.domain.user.controller;

import com.team2.grabtablecustomer.domain.user.dto.UserDto;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import com.team2.grabtablecustomer.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public UserResultDto insertUser(UserDto userDto) {
        return userService.insertUser(userDto);
    }
}
