package com.team2.grabtablecustomer.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResultDto {
    private String result;
    private ReviewDto reviewDto;
    private List<ReviewDto> reviewDtoList;
    private Long count;
}
