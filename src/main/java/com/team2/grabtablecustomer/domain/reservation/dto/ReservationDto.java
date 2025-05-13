package com.team2.grabtablecustomer.domain.reservation.dto;

import com.team2.grabtablecustomer.domain.user.entity.User;
import lombok.Data;

import java.util.Date;

@Data
public class ReservationDto {

    private Long reservationId;
    private Long userId;
    private Long storeId;
    private Date visitDate;
    private Long slotId;
    private Date createdAt;

}
