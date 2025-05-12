package com.team2.grabtablecustomer.domain.reservation.service;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationRequestDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationResponseDto;
import com.team2.grabtablecustomer.domain.reservation.entity.Reservation;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationRepository;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

    // 오픈시간(10:00) ~ 마감시간(22:00) 고정
    private static final LocalTime OPEN_TIME = LocalTime.of(10, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(22, 0);
    private static final Duration TIME_UNIT = Duration.ofHours(1);

    private static List<LocalTime> generateSlots(LocalTime from, LocalTime to) {
        List<LocalTime> slots = new ArrayList<>();
        for (LocalTime time = from; !time.isAfter(to.minus(TIME_UNIT)); time = time.plus(TIME_UNIT)) {
            slots.add(time);
        }
        return slots;
    }
    
    // 영업시간 고정
    private static final List<LocalTime> ALL_TIMES = generateSlots(OPEN_TIME, CLOSE_TIME);

    // 등급에 따른 이용시간 리스트 리턴
    private List<LocalTime> getAvailableTimesByLevel(String level) {
        return switch (level) {
            case "GOLD" -> ALL_TIMES;
            case "SILVER" -> ALL_TIMES.stream() // 실버는 12:00 ~ 22:00
                    .filter(t -> !t.isBefore(LocalTime.of(12, 0))).collect(Collectors.toList());
            case "BRONZE" -> ALL_TIMES.stream() // 브론즈는 15:00 ~ 22:00
                    .filter(t -> !t.isBefore(LocalTime.of(15, 0))).collect(Collectors.toList());
            default -> List.of();
        };
    }
    
    // 허용된 시간대 중 예약되지 않은 시간만 걸러내는 메서드
    private List<LocalTime> filterReservedTimes(List<LocalTime> allowedTimes, Store store, LocalDate date) {
        // 해당 날짜의 하루치 예약 내역을 모두 가져온다.
        List<Reservation> reserved = reservationRepository.findByStoreAndVisitDateBetween(
                store, date.atStartOfDay(), date.atTime(23, 59)
        );
        // 예약 객체의 visiteDate에서 시간만 추출한다.
        Set<LocalTime> reservedTimes = reserved.stream()
                .map(r -> r.getVisitDate().toLocalTime())
                .collect(Collectors.toSet());
        // 가능한 시간에서 예약된 시간 제거 (즉, 예약 가능한 시간들만 리턴한다)
        return allowedTimes.stream()
                .filter(t -> !reservedTimes.contains(t))
                .collect(Collectors.toList());
    }

    @Override
    public List<LocalTime> getGoldAvailableTimes(Long storeId, LocalDate date) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게 없음"));
        List<LocalTime> allowed = getAvailableTimesByLevel("GOLD");
        return filterReservedTimes(allowed, store, date);
    }

    @Override
    public List<LocalTime> getSilverAvailableTimes(Long storeId, LocalDate date) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게 없음"));
        List<LocalTime> allowed = getAvailableTimesByLevel("SILVER");
        return filterReservedTimes(allowed, store, date);
    }

    @Override
    public List<LocalTime> getBronzeAvailableTimes(Long storeId, LocalDate date) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new IllegalArgumentException("가게 없음"));
        List<LocalTime> allowed = getAvailableTimesByLevel("BRONZE");
        return filterReservedTimes(allowed, store, date);
    }


    @Override
    public void createReservation(String userEmail, ReservationRequestDto dto) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("고객을 찾을 수 없어요.."));

        Store store = storeRepository.findById(dto.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("해당 가게를 찾을 수 없어요.."));

        Reservation reservation = Reservation.builder()
                .user(user)
                .store(store)
                .visitDate(dto.getVisitDate())
                .build();

        reservationRepository.save(reservation);
    }

    @Override
    public List<ReservationResponseDto> getMyReservations(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("고객을 찾을 수 없어요."));

        return reservationRepository.findByUserWithStore(user).stream()
                .map(reservation -> ReservationResponseDto.builder()
                        .reservationId(reservation.getReservationId())
                        .storeName(reservation.getStore().getName())
                        .visitDate(reservation.getVisitDate())
                        .createdAt(reservation.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }




}
