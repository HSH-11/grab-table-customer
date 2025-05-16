package com.team2.grabtablecustomer.domain.user.controller;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.domain.user.dto.UserDto;
import com.team2.grabtablecustomer.domain.user.dto.UserInfoDto;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import com.team2.grabtablecustomer.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserResultDto> insertUser(UserDto userDto) {
        UserResultDto userResultDto = userService.insertUser(userDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResultDto);
    }

    /** 로그인된 사용자를 세션에서 불러와서 getEmail 한 후, DB에서 해당 사용자 정보 불러오기 */
    @GetMapping
    public ResponseEntity<UserResultDto> getLoggedInUser(@AuthenticationPrincipal CustomerUserDetails userDetails) {
        String email = userDetails.getUsername();   // 이게 이메일 받아오는것 (user의 name이 아니라 인증할때 사용하는 email이 username에 담겨있음. UserDetails 정책)

        UserResultDto resultDto = userService.getUserByEmail(email);
        return ResponseEntity.ok(resultDto);
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
