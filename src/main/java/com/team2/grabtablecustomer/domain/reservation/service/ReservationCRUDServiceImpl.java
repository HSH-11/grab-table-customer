package com.team2.grabtablecustomer.domain.reservation.service;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationResultDto;
import com.team2.grabtablecustomer.domain.reservation.entity.Reservation;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationRepository;
import com.team2.grabtablecustomer.domain.reservation.entity.ReservationSlot;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationSlotRepository;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import com.team2.grabtablecustomer.domain.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class ReservationCRUDServiceImpl implements ReservationCRUDService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;

    // TODO: 이렇게 타 도메인의 Repository들을 다 주입받아도 되나? 커플링 너무 높아지는게 아닌지 우려됨
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReservationSlotRepository reservationSlotRepository;

    @Override
    @Transactional
    public ReservationResultDto insertReservation(String email, ReservationDto reservationDto) {
        ReservationResultDto reservationResultDto = new ReservationResultDto();
        UserResultDto userResultDto = userService.getUserByEmail(email);

        try {
            if (userResultDto.getUserDto() == null) {
                reservationResultDto.setResult("User not found");
                return reservationResultDto;
            }

            User user = userRepository.findById(reservationDto.getUserId())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

            Store store = storeRepository.findById(reservationDto.getStoreId())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 매장입니다."));

            ReservationSlot slot = reservationSlotRepository.findById(reservationDto.getSlotId())
                    .orElseThrow(() -> new RuntimeException("존재하지 않는 슬롯입니다."));

            Reservation reservation = Reservation.builder()
                    .user(user)
                    .store(store)
                    .reservationSlot(slot)
                    .visitDate(reservationDto.getVisitDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())  // TODO: Date, LocalDateTime 통일 필요
                    .build();

            reservationRepository.save(reservation);
            reservationResultDto.setResult("success");
            reservationResultDto.setReservationDto(reservationDto);

        } catch (Exception e) {
            e.printStackTrace();
            reservationResultDto.setResult("fail");
        }

        return reservationResultDto;
    }


    @Override
    public ReservationResultDto getReservationByEmail(String email) {
        return null;
    }

    @Override
    public ReservationResultDto getReservationByEmailAndStoreId(String email, Long storeId) {
        return null;
    }


    @Override
    public ReservationResultDto updateReservation(ReservationDto reservationDto) {
        return null;
    }

    @Override
    public ReservationResultDto deleteReservation(Long reservationId) {
        return null;
    }
}
