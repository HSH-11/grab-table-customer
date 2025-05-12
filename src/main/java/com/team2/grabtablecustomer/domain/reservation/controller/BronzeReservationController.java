package com.team2.grabtablecustomer.domain.reservation.controller;

import com.team2.grabtablecustomer.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/bronze/reservations")
@RequiredArgsConstructor
public class BronzeReservationController {

    private final ReservationService reservationService;

    @GetMapping("/available-times")
    public ResponseEntity<List<LocalTime>> getAvailableTimes(
            @RequestParam("storeId") Long storeId,
            //@RequestParam: ?date=2025-05-10 형식으로 들어오는 쿼리를 받겠다는 의미
            //ISO.DATE는 String으로 들어온 날짜를 LocalDate로 파싱할 때, yyyy-MM-dd 포맷 처리
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<LocalTime> times = reservationService.getBronzeAvailableTimes(storeId, date);
        return ResponseEntity.ok(times);
    }
}
