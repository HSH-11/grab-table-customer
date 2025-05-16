package com.team2.grabtablecustomer.review;

import com.team2.grabtablecustomer.config.CustomerUserDetails;
import com.team2.grabtablecustomer.domain.menu.entity.Menu;
import com.team2.grabtablecustomer.domain.menu.repository.MenuRepository;
import com.team2.grabtablecustomer.domain.reservation.entity.Reservation;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationRepository;
import com.team2.grabtablecustomer.domain.review.dto.ReviewDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewRegisterDto;
import com.team2.grabtablecustomer.domain.review.dto.ReviewResultDto;
import com.team2.grabtablecustomer.domain.review.entity.Review;
import com.team2.grabtablecustomer.domain.review.repository.ReviewRepository;
import com.team2.grabtablecustomer.domain.review.service.ReviewServiceImpl;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private UserRepository userRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock private MenuRepository menuRepository;
    @Mock private ReservationRepository reservationRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @Test
    @DisplayName("가게 ID로 리뷰 리스트 조회")
    void findByStoreId() {
        Store store = Store.builder()
                .storeId(1L)
                .name("한식당")
                .location("서울")
                .type("한식")
                .build();

        Menu menu = Menu.builder()
                .menuId(1L)
                .name("비빔밥")
                .price(9000)
                .store(store)
                .build();

        User user = User.builder()
                .userId(10L)
                .name("홍길동")
                .email("hong@test.com")
                .build();

        Review review = Review.builder()
                .reviewId(1L)
                .content("맛있어요")
                .user(user)
                .store(store)
                .menu(menu)
                .build();


        when(storeRepository.findById(1L)).thenReturn(Optional.of(store));
        when(reviewRepository.findByStoreId(1L)).thenReturn(List.of(review));

        ReviewResultDto result = reviewService.findByStoreId(1L);

        assertThat(result.getResult()).isEqualTo("success");
        assertThat(result.getReviewDtoList()).hasSize(1);
        assertThat(result.getReviewDtoList().get(0).getContent()).isEqualTo("맛있어요");
    }

    @Test
    @DisplayName("메뉴 ID로 리뷰 리스트 조회")
    void findByMenuIdTest() {
        Menu menu = Menu.builder().menuId(1L).build();
        Review review = Review.builder()
                .reviewId(1L)
                .content("맛있어요")
                .menu(menu)
                .user(User.builder().userId(10L).build())
                .store(Store.builder().storeId(2L).build())
                .build();

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));
        when(reviewRepository.findByMenuId(1L)).thenReturn(List.of(review));

        ReviewResultDto result = reviewService.findByMenuId(1L);

        assertThat(result.getResult()).isEqualTo("success");
        assertThat(result.getReviewDtoList()).hasSize(1);
    }

    @Test
    @DisplayName("유저 ID로 리뷰 리스트 조회")
    void findByUserIdTest() {
        User user = User.builder().userId(1L).build();
        Review review = Review.builder()
                .reviewId(1L)
                .content("괜찮아요")
                .user(user)
                .store(Store.builder().storeId(3L).build())
                .menu(Menu.builder().menuId(5L).build())
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(User.builder().userId(1L).build()));
        when(reviewRepository.findByUserId(1L)).thenReturn(List.of(review));

        ReviewResultDto result = reviewService.findByUserId(1L);

        assertThat(result.getResult()).isEqualTo("success");
        assertThat(result.getReviewDtoList()).hasSize(1);
    }

    @Test
    @DisplayName("리뷰 ID로 리뷰 단건 조회")
    void findByReviewIdTest() {
        Review review = Review.builder()
                .reviewId(1L)
                .content("짱이에요")
                .user(User.builder().userId(100L).build())
                .menu(Menu.builder().menuId(3L).build())
                .store(Store.builder().storeId(2L).build())
                .build();

        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        ReviewResultDto result = reviewService.findByReviewId(1L);

        assertThat(result.getResult()).isEqualTo("success");
        assertThat(result.getReviewDto().getReviewId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("리뷰 등록 성공 - 이미지 없이")
    void insertReviewWithoutImage() throws IOException {
        // given
        Long storeId = 1L;
        Long menuId = 2L;
        Long reservationId = 3L;
        String email = "test@example.com";

        CustomerUserDetails userDetails = mock(CustomerUserDetails.class);
        when(userDetails.getUsername()).thenReturn(email);

        User user = User.builder()
                .userId(10L)
                .email(email)
                .build();

        Store store = Store.builder()
                .storeId(storeId)
                .build();

        Menu menu = Menu.builder()
                .menuId(menuId)
                .store(store)
                .build();

        Reservation reservation = Reservation.builder()
                .reservationId(reservationId)
                .user(user)
                .status("after")
                .build();

        ReviewRegisterDto dto = new ReviewRegisterDto();
        dto.setContent("맛있었습니다!");
        dto.setImageFile(new MockMultipartFile("imageFile", "", "image/png", new byte[0]));

        Review savedReview = Review.builder()
                .reviewId(99L)
                .user(user)
                .store(store)
                .menu(menu)
                .reservation(reservation)
                .content(dto.getContent())
                .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(menuRepository.findById(menuId)).thenReturn(Optional.of(menu));
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));
        when(reviewRepository.save(any(Review.class))).thenReturn(savedReview);

        // when
        ReviewResultDto result = reviewService.insertReview(userDetails, storeId, menuId, reservationId, dto);

        // then
        assertThat(result.getResult()).isEqualTo("success");
        // 이 서비스는 reviewDto를 반환하지 않으므로 null임
        assertThat(result.getReviewDto()).isNull();
    }


}

