package com.team2.grabtablecustomer.reservation;

import com.team2.grabtablecustomer.domain.reservation.dto.ReservationDto;
import com.team2.grabtablecustomer.domain.reservation.dto.ReservationResultDto;
import com.team2.grabtablecustomer.domain.reservation.entity.Reservation;
import com.team2.grabtablecustomer.domain.reservation.entity.ReservationSlot;
import com.team2.grabtablecustomer.domain.reservation.entity.ReservationSlot.MembershipLevel;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationRepository;
import com.team2.grabtablecustomer.domain.reservation.repository.ReservationSlotRepository;
import com.team2.grabtablecustomer.domain.reservation.service.ReservationCRUDServiceImpl;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import com.team2.grabtablecustomer.domain.user.dto.UserDto;
import com.team2.grabtablecustomer.domain.user.dto.UserResultDto;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import com.team2.grabtablecustomer.domain.user.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationCRUDServiceTest {

    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private ReservationSlotRepository slotRepository;
    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationCRUDServiceImpl service;

    @Test
    @DisplayName("insertReservation: User not found")
    void insertReservation_userNotFound() {
        String email = "noone@example.com";
        ReservationDto dto = ReservationDto.builder().storeId(1L).visitDate("2025-01-01").slotId(10L).build();

        given(userService.getUserByEmail(email))
                .willReturn(new UserResultDto()); // userDto == null

        ReservationResultDto result = service.insertReservation(email, dto);

        assertThat(result.getResult()).isEqualTo("User not found");
        assertThat(result.getReservationDto()).isNull();
    }

    @Test
    @DisplayName("insertReservation: success")
    void insertReservation_success() throws Exception {
        String email = "user@example.com";
        ReservationDto dto = ReservationDto.builder()
                .storeId(2L)
                .visitDate("2025-02-02")
                .slotId(20L)
                .build();

        // UserResultDto with userDto non-null
        UserDto userDto = UserDto.builder().email(email).build();
        given(userService.getUserByEmail(email))
                .willReturn(UserResultDto.builder().userDto(userDto).build());

        // find user entity
        User user = new User();
        user.setEmail(email);
        given(userRepository.findByEmail(email)).willReturn(Optional.of(user));

        // find store
        Store store = new Store();
        store.setStoreId(2L);
        given(storeRepository.findById(2L)).willReturn(Optional.of(store));

        // find slot
        ReservationSlot slot = ReservationSlot.builder()
                .slotId(20L)
                .store(store)
                .build();
        given(slotRepository.findById(20L)).willReturn(Optional.of(slot));

        // save reservation
        Reservation saved = Reservation.builder().reservationId(99L).user(user).store(store).reservationSlot(slot).visitDate(new SimpleDateFormat("yyyy-MM-dd").parse("2025-02-02")).status("before").build();
        given(reservationRepository.save(any(Reservation.class))).willReturn(saved);

        ReservationResultDto result = service.insertReservation(email, dto);

        assertThat(result.getResult()).isEqualTo("success");
        assertThat(result.getReservationDto()).isEqualTo(dto);
        then(reservationRepository).should().save(any(Reservation.class));
    }

    @Test
    @DisplayName("getReservationByEmail: User not found")
    void getReservationByEmail_userNotFound() {
        String email = "noone@example.com";

        given(userService.getUserByEmail(email))
                .willReturn(new UserResultDto()); // userDto == null

        ReservationResultDto result = service.getReservationByEmail(email);

        assertThat(result.getResult()).isEqualTo("User not found");
        assertThat(result.getReservationDtoList()).isNull();
    }

    @Test
    @DisplayName("getReservationByEmail: no reservations")
    void getReservationByEmail_noReservation() {
        String email = "user@example.com";
        UserDto userDto = UserDto.builder().email(email).build();
        given(userService.getUserByEmail(email))
                .willReturn(UserResultDto.builder().userDto(userDto).build());

        given(reservationRepository.findWithSlotAndStoreByUserEmail(email))
                .willReturn(List.of());

        ReservationResultDto result = service.getReservationByEmail(email);

        assertThat(result.getResult()).isEqualTo("no reservation");
        assertThat(result.getReservationDtoList()).isNull();
    }

    @Test
    @DisplayName("getReservationByEmail: success")
    void getReservationByEmail_success() {
        String email = "user@example.com";
        UserDto userDto = UserDto.builder().email(email).build();
        given(userService.getUserByEmail(email))
                .willReturn(UserResultDto.builder().userDto(userDto).build());

        // prepare one reservation
        Reservation r = Reservation.builder()
                .reservationId(5L)
                .user(new User() {{ setEmail(email); }})
                .store(new Store() {{ setStoreId(3L); setName("S"); }})
                .reservationSlot(new ReservationSlot() {{ setSlotId(7L); setStartTime("10"); }})
                .visitDate(new Date())
                .status("before")
                .build();
        given(reservationRepository.findWithSlotAndStoreByUserEmail(email))
                .willReturn(List.of(r));

        ReservationResultDto result = service.getReservationByEmail(email);

        assertThat(result.getResult()).isEqualTo("success");
        assertThat(result.getReservationDtoList()).hasSize(1)
                .extracting("reservationId", "storeId", "slotId", "status")
                .contains(tuple(5L, 3L, 7L, "before"));
    }

    @Test
    @DisplayName("deleteReservation: success")
    void deleteReservation_success() {
        String email = "user@example.com";
        Long resId = 55L;
        Reservation r = Reservation.builder()
                .reservationId(resId)
                .user(new User() {{ setEmail(email); }})
                .build();
        given(reservationRepository.findWithUserById(resId))
                .willReturn(Optional.of(r));

        ReservationResultDto result = service.deleteReservation(email, resId);

        assertThat(result.getResult()).isEqualTo("success");
        then(reservationRepository).should().delete(r);
    }

    @Test
    @DisplayName("deleteReservation: AccessDenied")
    void deleteReservation_accessDenied() {
        String email = "user@example.com";
        Long resId = 66L;
        Reservation r = Reservation.builder()
                .reservationId(resId)
                .user(new User() {{ setEmail("other@example.com"); }})
                .build();
        given(reservationRepository.findWithUserById(resId))
                .willReturn(Optional.of(r));

        ReservationResultDto result = service.deleteReservation(email, resId);

        assertThat(result.getResult()).isEqualTo("fail");
        then(reservationRepository).should(never()).delete(any());
    }

    @Test
    @DisplayName("deleteReservation: not found")
    void deleteReservation_notFound() {
        Long resId = 77L;
        given(reservationRepository.findWithUserById(resId))
                .willReturn(Optional.empty());

        ReservationResultDto result = service.deleteReservation("user@example.com", resId);

        assertThat(result.getResult()).isEqualTo("fail");
        then(reservationRepository).should(never()).delete(any());
    }
}
