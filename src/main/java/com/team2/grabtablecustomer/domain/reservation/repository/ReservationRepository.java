package com.team2.grabtablecustomer.domain.reservation.repository;

import com.team2.grabtablecustomer.domain.reservation.entity.Reservation;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("select r from Reservation r join fetch r.store where r.user = :user")
    List<Reservation> findByUserWithStore(@Param("user") User user);
    
    // 지정된 가게의 예약 중, 특정 날짜/시간에 해당하는 예약 목록 조회
    List<Reservation> findByStoreAndVisitDateBetween(Store store, LocalDateTime start, LocalDateTime end);


}
