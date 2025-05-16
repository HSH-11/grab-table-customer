package com.team2.grabtablecustomer.store;


import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class StoreIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StoreRepository storeRepository;

    @Test
    @DisplayName("GET /api/stores 요청 시 전체 가게 목록을 반환한다")
    void findAll() throws Exception {

        Store store = Store.builder()
                .name("통합테스트 가게")
                .location("서울시 강남구")
                .type("양식")
                .build();
        storeRepository.save(store);

        mockMvc.perform(get("/api/stores")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.storeDtoList").isArray());
    }

    @Test
    @DisplayName("GET /api/stores/detail/{storeId} 요청 시 해당 ID의 가게를 반환한다")
    void findStoreById() throws Exception {
        Store store = Store.builder()
                .name("ID조회 가게")
                .location("수원시")
                .type("일식")
                .build();
        Store savedStore = storeRepository.save(store);

        mockMvc.perform(get("/api/stores/detail/" + savedStore.getStoreId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").value(savedStore.getStoreId()))
                .andExpect(jsonPath("$.name").value("ID조회 가게"))
                .andExpect(jsonPath("$.location").value("수원시"))
                .andExpect(jsonPath("$.type").value("일식"));
    }

    @Test
    @DisplayName("GET /api/stores/{searchType} 요청 시 타입별 가게 목록을 반환한다")
    void findByType() throws Exception {
        Store store = Store.builder()
                .name("한식당")
                .location("인천")
                .type("중식")
                .build();
        storeRepository.save(store);
        // 한식은 없는 상태
        mockMvc.perform(get("/api/stores/type/한식"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.storeDtoList").isArray())
                .andExpect(jsonPath("$.storeDtoList.length()").value(0));
    }
}
