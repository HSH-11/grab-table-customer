package com.team2.grabtablecustomer.store;

import com.team2.grabtablecustomer.domain.store.dto.StoreDto;
import com.team2.grabtablecustomer.domain.store.dto.StoreResultDto;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import com.team2.grabtablecustomer.domain.store.service.StoreServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    @Test
    @DisplayName("전체 가게 목록 조회 테스트")
    void findAllTest(){
        Store store1 = Store.builder()
                .storeId(1L)
                .name("가게1")
                .location("서울")
                .type("한식")
                .build();

        Store store2 = Store.builder()
                .storeId(2L)
                .name("가게2")
                .location("부산")
                .type("중식")
                .build();

        given(storeRepository.findAll()).willReturn(List.of(store1, store2));

        StoreResultDto result = storeService.findAll();

        assertEquals("success", result.getResult());
        assertEquals(2, result.getStoreDtoList().size());
    }

    @Test
    @DisplayName("storeId로 개별 가게 조회")
    void testFindByStoreId() {
        Store store = Store.builder()
                .storeId(1L)
                .name("테스트가게")
                .location("서울")
                .type("양식")
                .build();

        given(storeRepository.findById(1L)).willReturn(Optional.of(store));

        StoreDto dto = storeService.findByStoreId(1L);

        assertEquals("테스트가게", dto.getName());
        assertEquals("서울", dto.getLocation());
        assertEquals("양식", dto.getType());
    }
    @Test
    @DisplayName("카테고리로 가게 목록 조회")
    void findByType() {
        String type = "한식";
        Store mockStore = Store.builder()
                .storeId(1L)
                .name("한식당")
                .location("서울")
                .type(type)
                .build();

        List<Store> mockStoreList = List.of(mockStore);

        given(storeRepository.findByType(type)).willReturn(mockStoreList);

        StoreResultDto resultDto = storeService.findByType(type);

        assertThat(resultDto).isNotNull();
        assertThat(resultDto.getResult()).isEqualTo("success");
        assertThat(resultDto.getStoreDtoList()).hasSize(1);
        assertThat(resultDto.getStoreDtoList().get(0).getName()).isEqualTo("한식당");
    }


}
