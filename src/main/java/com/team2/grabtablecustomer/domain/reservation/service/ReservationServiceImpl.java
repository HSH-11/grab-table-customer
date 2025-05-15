package com.team2.grabtablecustomer.domain.reservation.service;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationSlotResponseDto;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationRepository;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationSlotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationSlotRepository slotRepository;
    private final ReservationRepository reservationRepository;

    private static final List<String> LEVEL_PRIORITY = List.of("BRONZE", "SILVER", "GOLD");

    // 자신의 등급 이하도 예약 가능하게 끔 보여줘야함
    @Override
    public List<ReservationSlotResponseDto> getAvailableSlotsByGrade(Long storeId, String grade, String visitDate) {
        List<String> LEVEL_PRIORITY = List.of("BRONZE", "SILVER", "GOLD");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate;
        try {
            parsedDate = sdf.parse(visitDate);
        } catch (ParseException e) {
            throw new RuntimeException("잘못된 날짜 형식입니다: " + visitDate);
        }


        return slotRepository.findByStore_StoreId(storeId).stream()
                .filter(slot -> {
                    String allowed = slot.getAllowedMembership().name();
                    return LEVEL_PRIORITY.indexOf(allowed) <= LEVEL_PRIORITY.indexOf(grade);
                })
                .map(slot -> {
                    boolean isReserved = reservationRepository.existsByStore_StoreIdAndVisitDateAndReservationSlot_SlotId(slot.getStore().getStoreId(), parsedDate, slot.getSlotId());
                    System.out.println(visitDate);
                    System.out.println(parsedDate);
                    System.out.println(slot.getStore().getStoreId() + " " + parsedDate + " " +  slot.getSlotId());

                    return ReservationSlotResponseDto.builder()
                            .slotId(slot.getSlotId())
                            .storeId(slot.getStore().getStoreId())
                            .startTime(slot.getStartTime())
                            .endTime(slot.getEndTime())
                            .reserved(isReserved)
                            .build();
                })
                .collect(Collectors.toList());
    }

}
