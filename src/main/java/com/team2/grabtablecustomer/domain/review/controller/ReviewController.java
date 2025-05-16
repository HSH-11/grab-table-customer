package com.team2.grabtablecustomer.domain.review.controller;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.domain.review.dto.ReviewRegisterDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewResultDto;
import com.team2.grabtablecustomer.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {
    
    private final ReviewService reviewService;

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<ReviewResultDto> findByStoreId(@PathVariable("storeId") Long storeId) {
        return ResponseEntity.ok(reviewService.findByStoreId(storeId));
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<ReviewResultDto> findByMenuId(@PathVariable("menuId") Long menuId) {
        return ResponseEntity.ok(reviewService.findByMenuId(menuId));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ReviewResultDto> findByUserId(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(reviewService.findByUserId(userId));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResultDto> findByReviewId(@PathVariable("reviewId") Long reviewId) {
        return ResponseEntity.ok(reviewService.findByReviewId(reviewId));
    }

    @PostMapping("/stores/{storeId}/menus/{menuId}/reservation/{reservationId}")
    public ResponseEntity<ReviewResultDto> insertReview(
            @AuthenticationPrincipal CustomerUserDetails userDetails,
            @PathVariable("storeId") Long storeId,
            @PathVariable("menuId") Long menuId,
            @PathVariable("reservationId") Long reservationId,
            @ModelAttribute ReviewRegisterDto registerDto) throws IOException {
        return ResponseEntity.ok(reviewService.insertReview(userDetails, storeId, menuId, reservationId, registerDto));
    }

//    @PutMapping("/{reviewId}")
//    public ResponseEntity<ReviewResultDto> updateReview(
//            @AuthenticationPrincipal CustomerUserDetails userDetails,
//            @PathVariable("reviewId") Long reviewId,
//            @ModelAttribute ReviewRegisterDto registerDto) throws IOException {
//        return ResponseEntity.ok(reviewService.updateReview(userDetails, reviewId, registerDto));
//    }

//    @DeleteMapping("/{reviewId}")
//    public ResponseEntity<ReviewResultDto> deleteReview(@AuthenticationPrincipal CustomerUserDetails userDetails, @PathVariable("reviewId") Long reviewId) {
//        return ResponseEntity.ok(reviewService.deleteReview(userDetails, reviewId));
//    }
}
