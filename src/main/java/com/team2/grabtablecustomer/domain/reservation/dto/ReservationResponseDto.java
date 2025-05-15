package com.team2.grabtablecustomer.domain.reservation.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationResponseDto {
    private Long reservationId;
    private String storeName;
    private LocalDateTime visitDate;
    private LocalDateTime createdAt;
}
