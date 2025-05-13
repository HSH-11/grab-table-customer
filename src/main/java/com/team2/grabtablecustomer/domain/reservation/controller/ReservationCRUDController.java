package com.team2.grabtablecustomer.domain.reservation.controller;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationRequestDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationResultDto;
import com.team2.grabtablecustomer.domain.reservation.service.ReservationCRUDService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/{grade}/reservation/crud")
@RequiredArgsConstructor
public class ReservationCRUDController {

    private final ReservationCRUDService reservationCRUDService;

    @PostMapping
    public ResponseEntity<ReservationResultDto> insertReservation(
            @PathVariable("grade") String grade,
            @AuthenticationPrincipal CustomerUserDetails userDetails,
            @RequestBody ReservationDto reservationDto
    ) {
        String email = userDetails.getUsername();
        ReservationResultDto reservationResultDto = reservationCRUDService.insertReservation(email, reservationDto);

        return ResponseEntity.ok(reservationResultDto);
    }

}
