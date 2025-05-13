package com.team2.grabtablecustomer.domain.review.dto;

import lombok.Data;

import java.util.List;

@Data
public class ReviewResultDto {
    private String result;
    private ReviewDto reviewDto;
    private List<ReviewDto> reviewDtoList;
    private Long count;
}
