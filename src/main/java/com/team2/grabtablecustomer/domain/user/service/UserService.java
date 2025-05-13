package com.team2.grabtablecustomer.domain.user.service;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.domain.user.dto.UserDto;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

public interface UserService {

    UserResultDto insertUser(UserDto userDto);

    UserResultDto getUserByEmail(String email);
}
