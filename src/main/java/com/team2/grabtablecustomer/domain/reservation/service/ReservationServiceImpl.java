package com.team2.grabtablecustomer.domain.reservation.service;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationSlotResponseDto;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationSlotRepository slotRepository;

    private static final List<String> LEVEL_PRIORITY = List.of("BRONZE", "SILVER", "GOLD");

    // 자신의 등급 이하도 예약 가능하게 끔 보여줘야함
    @Override
    public List<ReservationSlotResponseDto> getAvailableSlotsByGrade(Long storeId, String grade) {
        List<String> LEVEL_PRIORITY = List.of("BRONZE", "SILVER", "GOLD");

        return slotRepository.findByStore_StoreId(storeId).stream()
                .filter(slot -> {
                    String allowed = slot.getAllowedMembership().name();
                    return LEVEL_PRIORITY.indexOf(allowed) <= LEVEL_PRIORITY.indexOf(grade);
                })
                .map(slot -> ReservationSlotResponseDto.builder()
                        .slotId(slot.getSlotId())
                        .storeId(slot.getStore().getStoreId())
                        .startTime(slot.getStartTime())
                        .endTime(slot.getEndTime())
                        .build())
                .collect(Collectors.toList());
    }







}
