package com.team2.grabtablecustomer.domain.reservation.dto;

import com.team2.grabtablecustomer.domain.reservation.entity.ReservationSlot.MembershipLevel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationSlotResponseDto {
    private Long slotId;
    private Long storeId;
    private String startTime;
    private String endTime;
    private boolean reserved;
    private MembershipLevel allowedMembership;
}
