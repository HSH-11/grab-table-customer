package com.team2.grabtablecustomer.domain.review.service;

import com.team2.grabtablecustomer.domain.review.dto.ReviewRegisterDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewResultDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

public interface ReviewService {
    ReviewResultDto findByStoreId(Long storeId);

    ReviewResultDto findByMenuId(Long menuId);

    ReviewResultDto findByUserId(Long userId);

    ReviewResultDto findByReviewId(Long reviewId);

    ReviewResultDto insertReview(UserDetails userDetails, ReviewRegisterDto registerDto) throws IOException;

    ReviewResultDto updateReview(UserDetails userDetails, ReviewRegisterDto registerDto) throws IOException;

    ReviewResultDto deleteReview(UserDetails userDetails, Long reviewId);

}
