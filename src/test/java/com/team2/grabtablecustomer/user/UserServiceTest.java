package com.team2.grabtablecustomer.user;

import com.team2.grabtablecustomer.domain.user.dto.UserDto;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import com.team2.grabtablecustomer.domain.user.entity.Membership;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.MembershipRepository;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import com.team2.grabtablecustomer.domain.user.service.UserService;
import com.team2.grabtablecustomer.domain.user.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void testInsertUser_success() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@email.com");
        userDto.setName("홍길동");
        userDto.setPassword("1234");
        userDto.setSelectedMembership("GOLD");

        when(userRepository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
        when(membershipRepository.findByName("BRONZE")).thenReturn(new Membership(1L, "BRONZE"));
        when(membershipRepository.findByName("SILVER")).thenReturn(new Membership(2L, "SILVER"));
        when(membershipRepository.findByName("GOLD")).thenReturn(new Membership(3L, "GOLD"));
        when(passwordEncoder.encode("1234")).thenReturn("encoded");

        when(userRepository.save(ArgumentMatchers.<User>any()))
                .thenAnswer(inv -> inv.getArgument(0));

        UserResultDto result = userService.insertUser(userDto);

        assertEquals("success", result.getResult());
        assertEquals("홍길동", result.getUserDto().getName());
    }

    @Test
    void testInsertUser_existEmail() {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@email.com");

        when(userRepository.findByEmail(userDto.getEmail()))
                .thenReturn(Optional.of(new User()));

        UserResultDto result = userService.insertUser(userDto);

        assertEquals("exist", result.getResult());
    }

    @Test
    void testGetUserByEmail_success() {
        Membership membership = new Membership(1L, "BRONZE");
        User user = User.builder()
                .userId(1L)
                .name("홍길동")
                .email("test@email.com")
                .createdAt(new Date())
                .memberships(List.of(membership))
                .build();

        when(userRepository.findByEmail("test@email.com"))
                .thenReturn(Optional.of(user));

        UserResultDto result = userService.getUserByEmail("test@email.com");

        assertEquals("success", result.getResult());
        assertEquals("홍길동", result.getUserDto().getName());
        assertEquals("BRONZE", result.getUserDto().getMemberships().get(0).getName());
    }
}

