package com.team2.grabtablecustomer.domain.review.service;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.domain.menu.entity.Menu;
import com.team2.grabtablecustomer.domain.menu.repository.MenuRepository;
import com.team2.grabtablecustomer.domain.review.dto.ReviewDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewRegisterDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewResultDto;
import com.team2.grabtablecustomer.domain.review.entity.Review;
import com.team2.grabtablecustomer.domain.review.repository.ReviewRepository;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
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
                        .userName(review.getUser().getName())
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
    public ReviewResultDto insertReview(CustomerUserDetails userDetails, Long storeId, Long menuId, ReviewRegisterDto registerDto) throws IOException {
        ReviewResultDto reviewResultDto = new ReviewResultDto();

        try {

            User user = userRepository.findByEmail(userDetails.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found : " + userDetails.getUsername()));

            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new RuntimeException("Store not found : " + storeId));

            Menu menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new RuntimeException("Menu not found : " + menuId));

            Review review = Review.builder()
                    .user(user)
                    .store(store)
                    .menu(menu)
                    .content(registerDto.getContent())
                    .image(registerDto.getImageFile().getBytes())
                    .imageContentType(registerDto.getImageFile().getContentType())
                    .build();

            reviewRepository.save(review);
            reviewResultDto.setResult("success");

        } catch (Exception e) {
            e.printStackTrace();
            reviewResultDto.setResult("fail");
        }

        return reviewResultDto;
    }

    @Override
    public ReviewResultDto updateReview(CustomerUserDetails userDetails, Long reviewId, ReviewRegisterDto registerDto) throws IOException {
        ReviewResultDto reviewResultDto = new ReviewResultDto();

        try {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found : " + reviewId));

            if (review.getUser().getEmail().equals(userDetails.getUsername())) {
                review.setContent(registerDto.getContent());
                review.setImage(registerDto.getImageFile().getBytes());
                review.setImageContentType(registerDto.getImageFile().getContentType());
                review.setUpdatedAt(LocalDateTime.now());

                reviewRepository.save(review);
                reviewResultDto.setResult("success");

            } else {
                reviewResultDto.setResult("no permission : " + reviewId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            reviewResultDto.setResult("fail");
        }

        return reviewResultDto;
    }

    @Override
    public ReviewResultDto deleteReview(CustomerUserDetails userDetails, Long reviewId) {
        ReviewResultDto reviewResultDto = new ReviewResultDto();

        try {
            Review review = reviewRepository.findById(reviewId)
                    .orElseThrow(() -> new RuntimeException("Review not found : " + reviewId));

            if (review.getUser().getEmail().equals(userDetails.getUsername())) {
                reviewRepository.delete(review);
                reviewResultDto.setResult("success");

            } else {
                reviewResultDto.setResult("no permission : " + reviewId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            reviewResultDto.setResult("fail");
        }

        return reviewResultDto;
    }
}
