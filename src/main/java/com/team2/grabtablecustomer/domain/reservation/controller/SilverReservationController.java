package com.team2.grabtablecustomer.domain.reservation.controller;

import com.team2.grabtablecustomer.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/silver/reservations")
@RequiredArgsConstructor
class SilverReservationController {

    private final ReservationService reservationService;

    @GetMapping("/available-times")
    public ResponseEntity<List<LocalTime>> getAvailableTimes(
            @RequestParam("storeId") Long storeId,
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<LocalTime> times = reservationService.getSilverAvailableTimes(storeId, date);
        return ResponseEntity.ok(times);
    }
}
