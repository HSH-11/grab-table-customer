package com.team2.grabtablecustomer.domain.user.controller;

import com.team2.grabtablecustomer.domain.user.dto.UserDto;
import com.team2.grabtablecustomer.domain.user.dto.UserInfoDto;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import com.team2.grabtablecustomer.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<UserResultDto> insertUser(UserDto userDto) {
        UserResultDto userResultDto = userService.insertUser(userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResultDto);
    }

    // 사용자 권한을 우선 순위를 만들어서 실버인 사용자가 DB에 같이 브론즈로 저장되어 있던 걸 건너뛰게끔 하였다.
    private static final List<String> LEVEL_PRIORITY = List.of("BRONZE", "SILVER", "GOLD");

    @GetMapping("/info")
    public ResponseEntity<UserInfoDto> getUserInfo(@AuthenticationPrincipal UserDetails userDetails) {
        String email = userDetails.getUsername();
        String level = userRepository.findByEmail(email)
                .orElseThrow()
                .getMemberships()
                .stream()
                .map(m -> m.getName())
                .max(Comparator.comparingInt(levelName -> LEVEL_PRIORITY.indexOf(levelName)))
                .orElse("BRONZE");

        return ResponseEntity.ok(new UserInfoDto(email, level));
    }

}
