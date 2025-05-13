package com.team2.grabtablecustomer.reservation;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationResultDto;
import com.team2.grabtablecustomer.domain.reservation.service.ReservationCRUDService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@Rollback
public class ReservationCRUDServiceTest {

    @Autowired
    private ReservationCRUDService reservationCRUDService;

    @Test
    void 예약_생성() {
        // given
        String email = "test@example.com";   // DB에 미리 존재하는 사용자
        ReservationDto dto = new ReservationDto();
        dto.setUserId(1L);                   // 실제 존재하는 userId, storeId, slotId를 넣을 것
        dto.setStoreId(1L);
        dto.setSlotId(1L);
        dto.setVisitDate(new Date());

        // when
        ReservationResultDto result = reservationCRUDService.insertReservation(email, dto);

        // then
        assertEquals("success", result.getResult());
    }


}
