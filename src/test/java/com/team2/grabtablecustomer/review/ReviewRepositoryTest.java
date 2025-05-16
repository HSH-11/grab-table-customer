package com.team2.grabtablecustomer.review;

import com.team2.grabtablecustomer.domain.review.entity.Review;
import com.team2.grabtablecustomer.domain.review.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReviewRepositoryTest {

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("storeId로 리뷰 목록을 조회한다")
    void testFindByStoreId() {
        List<Review> reviews = reviewRepository.findByStoreId(1L);

        assertThat(reviews).isNotNull();
        assertThat(reviews).allMatch(review -> review.getStore().getStoreId().equals(1L));
    }

    @Test
    @DisplayName("menuId로 리뷰 목록을 조회한다")
    void testFindByMenuId() {
        List<Review> reviews = reviewRepository.findByMenuId(2L);

        assertThat(reviews).isNotNull();
        assertThat(reviews).allMatch(review -> review.getMenu().getMenuId().equals(2L));
    }

    @Test
    @DisplayName("userId로 리뷰 목록을 조회한다")
    void testFindByUserId() {
        List<Review> reviews = reviewRepository.findByUserId(3L);

        assertThat(reviews).isNotNull();
        assertThat(reviews).allMatch(review -> review.getUser().getUserId().equals(3L));
    }
}

