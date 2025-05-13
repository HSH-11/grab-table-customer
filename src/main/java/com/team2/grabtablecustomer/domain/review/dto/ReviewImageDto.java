package com.team2.grabtablecustomer.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewImageDto {
    private byte[] data;
    private String contentType;
}
