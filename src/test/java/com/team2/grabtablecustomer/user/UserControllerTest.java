package com.team2.grabtablecustomer.user;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.config.MyAuthenticationFailureHandler;
import com.team2.grabtablecustomer.config.MyAuthenticationSuccessHandler;
import com.team2.grabtablecustomer.config.SecurityConfig;
import com.team2.grabtablecustomer.domain.user.controller.UserController;
import com.team2.grabtablecustomer.domain.user.dto.UserDto;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import com.team2.grabtablecustomer.domain.user.service.UserService;


import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@WebMvcTest(controllers = UserController.class)
@Import({SecurityConfig.class, MyAuthenticationSuccessHandler.class, MyAuthenticationFailureHandler.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository; // 혹시 controller 내부에서 직접 쓰고 있다면 추가

    // 보안 설정에 의존성이 있다면 추가
    @MockBean
    private UserDetailsService userDetailsService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    void testInsertUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setName("홍길동");
        userDto.setEmail("test@email.com");
        userDto.setPassword("1234");
        userDto.setSelectedMembership("GOLD");

        UserResultDto resultDto = new UserResultDto();
        resultDto.setResult("success");
        resultDto.setUserDto(userDto);

        when(userService.insertUser(any(UserDto.class))).thenReturn(resultDto);

        mockMvc.perform(post("/api/users/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", userDto.getName())
                        .param("email", userDto.getEmail())
                        .param("password", userDto.getPassword())
                        .param("selectedMembership", userDto.getSelectedMembership()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.userDto.name").value("홍길동"));
    }

    @Test
    void testGetLoggedInUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setEmail("test@email.com");
        userDto.setName("홍길동");

        UserResultDto dto = new UserResultDto();
        dto.setResult("success");
        dto.setUserDto(userDto);

        CustomerUserDetails userDetails = mock(CustomerUserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@email.com");
        when(userService.getUserByEmail("test@email.com")).thenReturn(dto);

        mockMvc.perform(get("/api/users")
                        .with(user(userDetails)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userDto.email").value("test@email.com"));
    }
}

