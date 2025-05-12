package com.team2.grabtablecustomer.domain.user.service;

import com.team2.grabtablecustomer.domain.user.dto.UserDto;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import com.team2.grabtablecustomer.domain.user.entity.Membership;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.MembershipRepository;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserResultDto insertUser(UserDto userDto) {

        UserResultDto userResultDto = new UserResultDto();

        try {
            Optional<User> optionalUser = userRepository.findByEmail(userDto.getEmail());

            if (optionalUser.isPresent()) {         // 이미 해당 email을 가진 User가 있으면
                userResultDto.setResult("exist");
                return userResultDto;
            }

            List<Membership> memberships = new ArrayList<>();
            Membership bronze = membershipRepository.findByName("BRONZE");
            memberships.add(bronze);

            if (userDto.getSelectedMembership().equals("SILVER") || userDto.getSelectedMembership().equals("GOLD")) {
                Membership silver = membershipRepository.findByName("SILVER");
                memberships.add(silver);
            }
            if (userDto.getSelectedMembership().equals("GOLD")) {
                Membership gold = membershipRepository.findByName("GOLD");
                memberships.add(gold);
            }

            User user = new User();

            user.setName(userDto.getName());
            user.setEmail(userDto.getEmail());
            String encodedPassword = passwordEncoder.encode(userDto.getPassword());
            user.setPassword(encodedPassword);

            user.setMemberships(memberships);   // User와 Membership 연결

            User savedUser = userRepository.save(user);
            userResultDto.setResult("success");
            userResultDto.setUserDto(userDto);

        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

    @Override
    public UserResultDto getUserByEmail(String email) {
        UserResultDto userResultDto = new UserResultDto();

        try {
            Optional<User> optionalUser = userRepository.findByEmail(email);

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                UserDto userDto = new UserDto();
                userDto.setUserId(user.getUserId());
                userDto.setName(user.getName());
                userDto.setEmail(user.getEmail());
                userDto.setCreatedAt(user.getCreatedAt());
                userDto.setMemberships(user.getMemberships());

                userResultDto.setResult("success");
                userResultDto.setUserDto(userDto);

            } else {
                userResultDto.setResult("notfound");
            }

        } catch (Exception e) {
            e.printStackTrace();
            userResultDto.setResult("fail");
        }

        return userResultDto;
    }

}
