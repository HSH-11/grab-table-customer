package com.team2.grabtablecustomer.domain.reservation.controller;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationRequestDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationResponseDto;
import com.team2.grabtablecustomer.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<List<ReservationResponseDto>> getMyReservations(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<ReservationResponseDto> reservations = reservationService.getMyReservations(userDetails.getUsername());
        return ResponseEntity.ok(reservations);
    }


}
