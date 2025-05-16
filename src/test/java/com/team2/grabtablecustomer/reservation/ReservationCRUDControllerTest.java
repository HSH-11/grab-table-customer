package com.team2.grabtablecustomer.reservation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.config.MyAuthenticationFailureHandler;
import com.team2.grabtablecustomer.config.MyAuthenticationSuccessHandler;
import com.team2.grabtablecustomer.config.SecurityConfig;
import com.team2.grabtablecustomer.domain.reservation.controller.ReservationCRUDController;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationResultDto;
import com.team2.grabtablecustomer.domain.reservation.service.ReservationCRUDService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ReservationCRUDController.class)
@Import({
        SecurityConfig.class,
        MyAuthenticationSuccessHandler.class,
        MyAuthenticationFailureHandler.class
})
class ReservationCRUDControllerTest {

    private static final String GRADE = "bronze";
    private static CustomerUserDetails TEST_USER;

    @BeforeAll
    static void setupUser() {
        // ROLE_BRONZE 권한을 가진 테스트 유저 생성
        TEST_USER = new CustomerUserDetails(
                "customer@example.com",
                "ignored-password",
                List.of(new SimpleGrantedAuthority("ROLE_BRONZE"))
        );
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationCRUDService reservationCRUDService;

    @Test
    @DisplayName("POST /api/bronze/reservation/crud - 예약 삽입 성공")
    void insertReservation_success() throws Exception {
        // 요청 DTO 준비
        ReservationDto reqDto = ReservationDto.builder()
                .storeId(10L)
                .visitDate("2025-05-20")
                .slotId(2L)
                .build();

        // 서비스가 반환할 결과 준비
        ReservationResultDto resDto = new ReservationResultDto();
        resDto.setResult("success");
        resDto.setReservationDto(reqDto);

        // 서비스 호출 스텁 설정
        given(reservationCRUDService.insertReservation(
                eq(TEST_USER.getUsername()), eq(reqDto)))
                .willReturn(resDto);

        // 실제 요청 및 검증
        mockMvc.perform(post("/api/{grade}/reservation/crud", GRADE)
                        .with(user(TEST_USER))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reqDto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.reservationDto.storeId").value(10));
    }

    @Test
    @DisplayName("GET  /api/bronze/reservation/crud/list - 사용자별 예약 조회")
    void getReservationByEmail_success() throws Exception {
        // 서비스 반환값 준비
        ReservationResultDto resDto = new ReservationResultDto();
        resDto.setResult("success");
        resDto.setReservationDtoList(Collections.emptyList());

        // 서비스 호출 스텁
        given(reservationCRUDService.getReservationByEmail(TEST_USER.getUsername()))
                .willReturn(resDto);

        // 요청 및 JSON 응답 검증
        mockMvc.perform(get("/api/{grade}/reservation/crud/list", GRADE)
                        .with(user(TEST_USER))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.reservationDtoList").isArray())
                .andExpect(jsonPath("$.reservationDtoList").isEmpty());
    }

    @Test
    @DisplayName("DELETE /api/bronze/reservation/crud/{id} - 예약 삭제 성공")
    void deleteReservation_success() throws Exception {
        Long reservationId = 55L;

        // 서비스가 반환할 결과 준비
        ReservationResultDto resDto = new ReservationResultDto();
        resDto.setResult("success");

        // 서비스 호출 스텁
        given(reservationCRUDService.deleteReservation(
                TEST_USER.getUsername(), reservationId))
                .willReturn(resDto);

        // 요청 및 결과 검증
        mockMvc.perform(delete("/api/{grade}/reservation/crud/{id}", GRADE, reservationId)
                        .with(user(TEST_USER))
                        .with(csrf())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.result").value("success"));
    }
}
