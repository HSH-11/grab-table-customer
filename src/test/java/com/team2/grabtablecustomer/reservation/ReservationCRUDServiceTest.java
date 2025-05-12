package com.team2.grabtablecustomer.reservation;

import com.team2.grabtablecustomer.domain.reservation.service.ReservationCRUDService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@SpringBootTest
@Transactional
@Rollback
public class ReservationCRUDServiceTest {

    @Autowired
    private ReservationCRUDService reservationCRUDService;

    @Test
    void 예약_생성() {
        // given

    }


}
