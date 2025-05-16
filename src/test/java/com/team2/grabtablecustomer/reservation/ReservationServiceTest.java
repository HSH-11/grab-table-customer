package com.team2.grabtablecustomer.domain.reservation.service;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationSlotResponseDto;
import com.team2.grabtablecustomer.domain.reservation.entity.ReservationSlot;
import com.team2.grabtablecustomer.domain.reservation.entity.ReservationSlot.MembershipLevel;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationRepository;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationSlotRepository;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationSlotRepository slotRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl service;

    @Test
    @DisplayName("getAvailableSlotsByGrade: 정상 동작, 등급 이하 슬롯만 필터링")
    void getAvailableSlotsByGrade_filtersByGrade() throws Exception {
        Long storeId = 42L;
        String visitDate = "2025-05-16";
        String grade = "SILVER";

        // 공통 Store 설정
        Store store = new Store();
        store.setStoreId(storeId);

        // 슬롯 생성: BRONZE, SILVER, GOLD
        ReservationSlot slotBronze = ReservationSlot.builder()
                .slotId(1L)
                .store(store)
                .startTime("08:00")
                .endTime("09:00")
                .allowedMembership(MembershipLevel.BRONZE)
                .build();
        ReservationSlot slotSilver = ReservationSlot.builder()
                .slotId(2L)
                .store(store)
                .startTime("09:00")
                .endTime("10:00")
                .allowedMembership(MembershipLevel.SILVER)
                .build();
        ReservationSlot slotGold = ReservationSlot.builder()
                .slotId(3L)
                .store(store)
                .startTime("10:00")
                .endTime("11:00")
                .allowedMembership(MembershipLevel.GOLD)
                .build();

        // stub: 리포지토리에서 모두 리턴
        given(slotRepository.findByStore_StoreId(storeId))
                .willReturn(List.of(slotBronze, slotSilver, slotGold));

        // 예약 여부: 모두 false
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = sdf.parse(visitDate);
        given(reservationRepository
                .existsByStore_StoreIdAndVisitDateAndReservationSlot_SlotId(eq(storeId), eq(parsed), anyLong()))
                .willReturn(false);

        // 실행
        List<ReservationSlotResponseDto> result =
                service.getAvailableSlotsByGrade(storeId, grade, visitDate);

        // 검증: BRONZE, SILVER만 포함, GOLD는 제외
        assertThat(result)
                .extracting(ReservationSlotResponseDto::getSlotId)
                .containsExactlyInAnyOrder(1L, 2L)
                .doesNotContain(3L);

        // reserved 필드는 false
        assertThat(result).allSatisfy(dto ->
                assertThat(dto.isReserved()).isFalse()
        );
    }

    @Test
    @DisplayName("getAvailableSlotsByGrade: 예약 여부 true/false 반영")
    void getAvailableSlotsByGrade_marksReservedCorrectly() throws Exception {
        Long storeId = 100L;
        String visitDate = "2025-12-25";
        String grade = "GOLD";

        Store store = new Store();
        store.setStoreId(storeId);

        ReservationSlot slotA = ReservationSlot.builder()
                .slotId(10L)
                .store(store)
                .startTime("12:00")
                .endTime("13:00")
                .allowedMembership(MembershipLevel.GOLD)
                .build();

        given(slotRepository.findByStore_StoreId(storeId))
                .willReturn(List.of(slotA));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parsed = sdf.parse(visitDate);
        // slotId=10은 예약되어 있고
        given(reservationRepository
                .existsByStore_StoreIdAndVisitDateAndReservationSlot_SlotId(storeId, parsed, 10L))
                .willReturn(true);

        List<ReservationSlotResponseDto> result =
                service.getAvailableSlotsByGrade(storeId, grade, visitDate);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getSlotId()).isEqualTo(10L);
        assertThat(result.get(0).isReserved()).isTrue();
    }

    @Test
    @DisplayName("getAvailableSlotsByGrade: 잘못된 날짜 포맷이면 예외 발생")
    void getAvailableSlotsByGrade_invalidDate_throws() {
        Long storeId = 5L;
        String badDate = "2025/05/16"; // 잘못된 포맷
        String grade = "BRONZE";

        assertThatThrownBy(() ->
                service.getAvailableSlotsByGrade(storeId, grade, badDate)
        ).isInstanceOf(RuntimeException.class)
                .hasMessageContaining("잘못된 날짜 형식입니다");
    }
}
