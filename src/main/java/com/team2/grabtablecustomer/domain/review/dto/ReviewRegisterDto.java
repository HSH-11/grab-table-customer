package com.team2.grabtablecustomer.domain.review.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRegisterDto {
    private String content;
    private MultipartFile imageFile;
}
