package com.team2.grabtablecustomer.domain.reservation.repository;

import com.team2.grabtablecustomer.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUser_Email(String email);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.reservationSlot " +
            "WHERE r.user.email = :email")
    List<Reservation> findWithSlotByUserEmail(@Param("email") String email);

    @Query("SELECT r FROM Reservation r " +
            "JOIN FETCH r.user " +
            "WHERE r.reservationId = :id")
    Optional<Reservation> findWithUserById(@Param("id") Long id);

}
