package com.team2.grabtablecustomer.domain.review.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class ReviewDto {
    private Long reviewId;
    // todo : 유저이름, 가게이름, 메뉴이름 으로??
    private Long userId;
    private String userName;
    private Long storeId;
    private Long menuId;
    // todo : 예약 연결
//    private Long reservationId;
    private String content;
    private Date createdAt;
    private Date updatedAt;

    private byte[] image;
    private String imageContentType;
}

