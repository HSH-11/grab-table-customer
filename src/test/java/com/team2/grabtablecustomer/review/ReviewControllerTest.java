package com.team2.grabtablecustomer.review;


import com.team2.grabtablecustomer.config.CustomerUserDetailsService;
import com.team2.grabtablecustomer.domain.review.controller.ReviewController;
import com.team2.grabtablecustomer.domain.review.dto.ReviewDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewRegisterDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewResultDto;
import com.team2.grabtablecustomer.domain.review.service.ReviewService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 비로그인 상태에서 메뉴에 대한 리뷰 조회만 접근 가능 (Security 설정)
// 리뷰 작성은 권한 인증을 받아야 하므로 테스트할 때는 무시하고 api 테스트
@WebMvcTest(ReviewController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;
    @MockBean private CustomerUserDetailsService customerUserDetailsService;

    @Test
    @DisplayName("storeId로 리뷰 조회 성공")
    void getReviewsByStoreId() throws Exception {
        ReviewResultDto mockResult = new ReviewResultDto();
        mockResult.setResult("success");
        mockResult.setReviewDtoList(Collections.emptyList());

        given(reviewService.findByStoreId(1L)).willReturn(mockResult);

        mockMvc.perform(get("/api/reviews/stores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"));
    }

    @Test
    @DisplayName("menuId로 리뷰 목록 조회")
    @WithMockUser
    void testFindByMenuId() throws Exception {
        ReviewDto dto = ReviewDto.builder()
                .reviewId(1L)
                .menuId(2L)
                .storeId(1L)
                .content("짬뽕이 맛있어요")
                .userId(100L)
                .build();

        ReviewResultDto mockResult = new ReviewResultDto();
        mockResult.setResult("success");
        mockResult.setReviewDtoList(List.of(dto));

        when(reviewService.findByMenuId(2L)).thenReturn(mockResult);

        // when & then
        mockMvc.perform(get("/api/reviews/menus/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.reviewDtoList[0].menuId").value(2))
                .andExpect(jsonPath("$.reviewDtoList[0].content").value("짬뽕이 맛있어요"));
    }


    @Test
    @DisplayName("userId로 리뷰 목록 조회")
    @WithMockUser
    void testFindByUserId() throws Exception {
        ReviewResultDto mockResult = new ReviewResultDto();
        mockResult.setResult("success");
        mockResult.setReviewDtoList(Collections.emptyList());

        when(reviewService.findByUserId(1L)).thenReturn(mockResult);

        mockMvc.perform(get("/api/reviews/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.reviewDtoList").isArray());
    }

    @Test
    @DisplayName("reviewId로 리뷰 단건 조회")
    @WithMockUser
    void testFindByReviewId() throws Exception {
        ReviewDto dto = ReviewDto.builder()
                .reviewId(10L)
                .storeId(1L)
                .menuId(2L)
                .userId(3L)
                .content("맛있어요!")
                .build();

        ReviewResultDto mockResult = new ReviewResultDto();
        mockResult.setResult("success");
        mockResult.setReviewDto(dto);

        when(reviewService.findByReviewId(10L)).thenReturn(mockResult);

        mockMvc.perform(get("/api/reviews/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.reviewDto.reviewId").value(10));
    }

    @Test
    @DisplayName("리뷰 작성")
    @WithMockUser(username = "user@example.com", roles = "BRONZE")
    void testInsertReview() throws Exception {
        ReviewRegisterDto registerDto = new ReviewRegisterDto();
        registerDto.setContent("정말 좋아요!");

        ReviewDto savedReview = ReviewDto.builder()
                .reviewId(1L)
                .content("정말 좋아요!")
                .userId(3L)
                .storeId(1L)
                .menuId(2L)
                .build();

        ReviewResultDto resultDto = new ReviewResultDto();
        resultDto.setResult("success");
        resultDto.setReviewDto(savedReview);

        when(reviewService.insertReview(any(), any(), any(), any(), any())).thenReturn(resultDto);

        mockMvc.perform(multipart("/api/reviews/stores/1/menus/2/reservation/3")
                        .param("content", "정말 좋아요!")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.reviewDto.content").value("정말 좋아요!"));
    }
}
