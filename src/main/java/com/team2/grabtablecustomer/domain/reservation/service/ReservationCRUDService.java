package com.team2.grabtablecustomer.domain.reservation.service;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationResultDto;

public interface ReservationCRUDService {

    ReservationResultDto insertReservation(String email, ReservationDto reservationDto);

    ReservationResultDto getReservationByEmail(String email);
    ReservationResultDto getReservationByEmailAndStoreId(String email, Long storeId);

    ReservationResultDto updateReservation(ReservationDto reservationDto);
    ReservationResultDto deleteReservation(String email, Long reservationId);

//    void testRepo();
}
