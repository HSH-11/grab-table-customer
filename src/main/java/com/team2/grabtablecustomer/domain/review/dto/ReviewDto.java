package com.team2.grabtablecustomer.domain.review.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ReviewDto {
    private Long reviewId;
    // todo : 유저이름, 가게이름, 메뉴이름 으로??
    private Long userId;
    private Long storeId;
    private Long menuId;
    // todo : 예약 연결
//    private Long reservationId;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    private byte[] image;
    private String imageContentType;
}

