package com.team2.grabtablecustomer.domain.reservation.entity;

import com.team2.grabtablecustomer.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "reservation_slot")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationSlot {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long slotId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id")
    private Store store;

    private Date startTime;
    private Date endTime;

    private String allowedMembership;
}
