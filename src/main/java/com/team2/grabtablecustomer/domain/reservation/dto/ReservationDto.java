package com.team2.grabtablecustomer.domain.reservation.dto;

import com.team2.grabtablecustomer.domain.user.entity.User;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ReservationDto {

    private Long reservationId;
    // private Long userId;     // 보안상 userId를 프론트에서 보내는것도 이상하고, 애초에 쓰질 않음
    private Long storeId;
    private String visitDate;   // TODO: (임시) String으로 받아서 ServiceImpl에서 parse 해놨음

    private Long slotId;
    private String slotStartTime;

    private Date createdAt;

    private String status;

}
