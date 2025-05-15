package com.team2.grabtablecustomer.domain.review.service;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.domain.review.dto.ReviewRegisterDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewResultDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

public interface ReviewService {
    ReviewResultDto findByStoreId(Long storeId);

    ReviewResultDto findByMenuId(Long menuId);

    ReviewResultDto findByUserId(Long userId);

    ReviewResultDto findByReviewId(Long reviewId);

    ReviewResultDto insertReview(CustomerUserDetails userDetails, Long storeId, Long menuId, Long reservationId, ReviewRegisterDto registerDto) throws IOException;

    ReviewResultDto updateReview(CustomerUserDetails userDetails, Long reviewId, ReviewRegisterDto registerDto) throws IOException;

    ReviewResultDto deleteReview(CustomerUserDetails userDetails, Long reviewId);

}
