package com.team2.grabtablecustomer.domain.review.service;

import com.team2.grabtablecustomer.domain.menu.repository.MenuRepository;
import com.team2.grabtablecustomer.domain.review.dto.ReviewDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewRegisterDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewResultDto;
import com.team2.grabtablecustomer.domain.review.entity.Review;
import com.team2.grabtablecustomer.domain.review.repository.ReviewRepository;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    @Override
    public ReviewResultDto findByStoreId(Long storeId) {
        ReviewResultDto reviewResultDto = new ReviewResultDto();

        try {
            storeRepository.findById(storeId)
                    .orElseThrow(() -> new RuntimeException("Store not found : " + storeId));

            List<Review> reviewList = reviewRepository.findByStoreId(storeId);
            List<ReviewDto> reveiwDtoList = new ArrayList<>();
            for (Review review : reviewList) {
                ReviewDto reviewDto = ReviewDto.builder()
                        .reviewId(review.getReviewId())
                        .userId(review.getUser().getUserId())
                        .storeId(review.getStore().getStoreId())
                        .menuId(review.getMenu().getMenuId())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(review.getUpdatedAt())
                        .image(review.getImage())
                        .imageContentType(review.getImageContentType())
                        .build();
                reveiwDtoList.add(reviewDto);
            }
            reviewResultDto.setReviewDtoList(reveiwDtoList);
            reviewResultDto.setResult("success");

        } catch (Exception e) {
            e.printStackTrace();
            reviewResultDto.setResult("fail");
        }

        return reviewResultDto;
    }

    @Override
    public ReviewResultDto findByMenuId(Long menuId) {
        ReviewResultDto reviewResultDto = new ReviewResultDto();

        try {
            menuRepository.findById(menuId)
                    .orElseThrow(() -> new RuntimeException("Menu not found : " + menuId));

            List<Review> reviewList = reviewRepository.findByMenuId(menuId);
            List<ReviewDto> reveiwDtoList = new ArrayList<>();
            for (Review review : reviewList) {
                ReviewDto reviewDto = ReviewDto.builder()
                        .reviewId(review.getReviewId())
                        .userId(review.getUser().getUserId())
                        .storeId(review.getStore().getStoreId())
                        .menuId(review.getMenu().getMenuId())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(review.getUpdatedAt())
                        .image(review.getImage())
                        .imageContentType(review.getImageContentType())
                        .build();
                reveiwDtoList.add(reviewDto);
            }
            reviewResultDto.setReviewDtoList(reveiwDtoList);
            reviewResultDto.setResult("success");

        } catch (Exception e) {
            e.printStackTrace();
            reviewResultDto.setResult("fail");
        }

        return reviewResultDto;
    }

    @Override
    public ReviewResultDto findByUserId(Long userId) {
        ReviewResultDto reviewResultDto = new ReviewResultDto();

        try {
            userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found : " + userId));

            List<Review> reviewList = reviewRepository.findByUserId(userId);
            List<ReviewDto> reveiwDtoList = new ArrayList<>();
            for (Review review : reviewList) {
                ReviewDto reviewDto = ReviewDto.builder()
                        .reviewId(review.getReviewId())
                        .userId(review.getUser().getUserId())
                        .storeId(review.getStore().getStoreId())
                        .menuId(review.getMenu().getMenuId())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(review.getUpdatedAt())
                        .image(review.getImage())
                        .imageContentType(review.getImageContentType())
                        .build();
                reveiwDtoList.add(reviewDto);
            }
            reviewResultDto.setReviewDtoList(reveiwDtoList);
            reviewResultDto.setResult("success");

        } catch (Exception e) {
            e.printStackTrace();
            reviewResultDto.setResult("fail");
        }

        return reviewResultDto;
    }

    @Override
    public ReviewResultDto findByReviewId(Long reviewId) {
        ReviewResultDto reviewResultDto = new ReviewResultDto();

        try {
            Optional<Review> optionalReview = reviewRepository.findById(reviewId);

            if (optionalReview.isPresent()) {
                Review review = optionalReview.get();
                ReviewDto reviewDto = ReviewDto.builder()
                        .reviewId(review.getReviewId())
                        .userId(review.getUser().getUserId())
                        .storeId(review.getStore().getStoreId())
                        .menuId(review.getMenu().getMenuId())
                        .content(review.getContent())
                        .createdAt(review.getCreatedAt())
                        .updatedAt(review.getUpdatedAt())
                        .image(review.getImage())
                        .imageContentType(review.getImageContentType())
                        .build();
                reviewResultDto.setReviewDto(reviewDto);
                reviewResultDto.setResult("success");
            }

        } catch (Exception e) {
            e.printStackTrace();
            reviewResultDto.setResult("fail");
        }

        return reviewResultDto;
    }

    @Override
    public ReviewResultDto insertReview(UserDetails userDetails, ReviewRegisterDto registerDto) throws IOException {
        return null;
    }

    @Override
    public ReviewResultDto updateReview(UserDetails userDetails, ReviewRegisterDto registerDto) throws IOException {
        return null;
    }

    @Override
    public ReviewResultDto deleteReview(UserDetails userDetails, Long reviewId) {
        return null;
    }
}
