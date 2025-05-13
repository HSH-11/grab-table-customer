package com.team2.grabtablecustomer.domain.reservation.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationRequestDto {
    private Long storeId;
    private LocalDateTime visitDate;
}
