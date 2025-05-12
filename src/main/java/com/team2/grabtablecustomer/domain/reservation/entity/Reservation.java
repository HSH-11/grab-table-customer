package com.team2.grabtablecustomer.domain.reservation.entity;

import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private LocalDateTime visitDate; // 방문할 날짜 및 시간

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt; // 예약 생성 시간
}
