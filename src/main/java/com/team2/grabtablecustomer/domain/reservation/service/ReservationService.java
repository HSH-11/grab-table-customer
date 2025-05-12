package com.team2.grabtablecustomer.domain.reservation.service;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationRequestDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationResponseDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationService {

    void createReservation(String userEmail, ReservationRequestDto dto);

    List<ReservationResponseDto> getMyReservations(String userEmail);

    List<LocalTime> getGoldAvailableTimes(Long storeId, LocalDate date);
    List<LocalTime> getSilverAvailableTimes(Long storeId, LocalDate date);
    List<LocalTime> getBronzeAvailableTimes(Long storeId, LocalDate date);

}
