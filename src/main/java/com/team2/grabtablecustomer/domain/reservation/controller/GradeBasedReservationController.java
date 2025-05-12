package com.team2.grabtablecustomer.domain.reservation.controller;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationSlotResponseDto;
import com.team2.grabtablecustomer.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/{grade}/reservations")
@RequiredArgsConstructor
public class GradeBasedReservationController {

    private final ReservationService reservationService;

    @GetMapping("/available-times")
    public ResponseEntity<List<ReservationSlotResponseDto>> getAvailableTimes(
            @PathVariable("grade") String grade,
            @RequestParam("storeId") Long storeId) {

        // 사용자가 요청한 grade에 따른 가능한 시간대 제공
        List<ReservationSlotResponseDto> slots = reservationService.getAvailableSlotsByGrade(storeId, grade.toUpperCase());

        return ResponseEntity.ok(slots);
    }
}

