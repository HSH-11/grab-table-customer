package com.team2.grabtablecustomer.reservation;

import com.team2.grabtablecustomer.config.MyAuthenticationFailureHandler;
import com.team2.grabtablecustomer.config.MyAuthenticationSuccessHandler;
import com.team2.grabtablecustomer.config.SecurityConfig;
import com.team2.grabtablecustomer.domain.reservation.controller.GradeBasedReservationController;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationSlotResponseDto;
import com.team2.grabtablecustomer.domain.reservation.entity.ReservationSlot.MembershipLevel;
import com.team2.grabtablecustomer.domain.reservation.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GradeBasedReservationController.class)
@Import({
        SecurityConfig.class,
        MyAuthenticationSuccessHandler.class,
        MyAuthenticationFailureHandler.class
})
class GradeBasedReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("GET /api/silver/reservations/available-times - SILVER 권한으로 접근")
    void getSilverSlots_asSilverRole() throws Exception {
        String grade = "silver";
        Long storeId = 5L;
        String visitDate = "2025-05-16";

        List<ReservationSlotResponseDto> slots = List.of(
                ReservationSlotResponseDto.builder()
                        .slotId(1L)
                        .storeId(storeId)
                        .startTime("09:00")
                        .endTime("10:00")
                        .allowedMembership(MembershipLevel.BRONZE)
                        .reserved(false)
                        .build()
        );
        given(reservationService.getAvailableSlotsByGrade(
                eq(storeId), eq(grade.toUpperCase()), eq(visitDate)))
                .willReturn(slots);

        mockMvc.perform(get("/api/{grade}/reservations/available-times", grade)
                        .with(user("silverUser").roles("SILVER"))
                        .param("storeId", storeId.toString())
                        .param("visitDate", visitDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].slotId").value(1));
    }

    @Test
    @DisplayName("GET /api/gold/reservations/available-times - GOLD 권한으로 접근")
    void getGoldSlots_asGoldRole() throws Exception {
        String grade = "gold";
        Long storeId = 3L;
        String visitDate = "2025-05-16";

        given(reservationService.getAvailableSlotsByGrade(
                eq(storeId), eq(grade.toUpperCase()), eq(visitDate)))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/{grade}/reservations/available-times", grade)
                        .with(user("goldUser").roles("GOLD"))
                        .param("storeId", storeId.toString())
                        .param("visitDate", visitDate)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }
}
