package com.team2.grabtablecustomer.domain.reservation.dto;

import com.team2.grabtablecustomer.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReservationDto {

    private Long reservationId;
    private Long storeId;
    private String storeName;
    private String visitDate;   // TODO: (임시) String으로 받아서 ServiceImpl에서 parse 해놨음

    private Long slotId;
    private String slotStartTime;

    private Date createdAt;

    private String status;

}
