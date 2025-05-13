package com.team2.grabtablecustomer.domain.review.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
public class ReviewRegisterDto {
    private String content;
    private MultipartFile imageFile;
}
