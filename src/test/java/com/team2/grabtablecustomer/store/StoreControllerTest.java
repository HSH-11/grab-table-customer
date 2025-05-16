package com.team2.grabtablecustomer.store;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.grabtablecustomer.config.MyAuthenticationFailureHandler;
import com.team2.grabtablecustomer.config.MyAuthenticationSuccessHandler;
import com.team2.grabtablecustomer.config.SecurityConfig;
import com.team2.grabtablecustomer.domain.store.controller.StoreController;
import com.team2.grabtablecustomer.domain.store.dto.StoreDto;
import com.team2.grabtablecustomer.domain.store.dto.StoreResultDto;
import com.team2.grabtablecustomer.domain.store.service.StoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// StoreController의 비로그인 상태에서도 모두 허용
@WebMvcTest(StoreController.class)
@Import({SecurityConfig.class, MyAuthenticationSuccessHandler.class, MyAuthenticationFailureHandler.class})
public class StoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StoreService storeService;

    @Test
    @DisplayName("전체 가게 목록을 반환한다.")
    void findAllStoresTest() throws Exception {
        StoreResultDto mockResult = new StoreResultDto();
        mockResult.setResult("success");
        mockResult.setStoreDtoList(Collections.emptyList());

        given(storeService.findAll()).willReturn(mockResult);

        mockMvc.perform(get("/api/stores"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"));
    }

    @Test
    @DisplayName("카테고리 별 가게 목록을 반환한다.")
    void findStoreByTypeTest() throws Exception {
        StoreDto store1 = StoreDto.builder()
                .storeId(5L)
                .name("금성반점")
                .location("경북")
                .type("중식")
                .build();

        StoreResultDto mockResult = new StoreResultDto();
        mockResult.setResult("success");
        mockResult.setStoreDtoList(List.of(store1));

        given(storeService.findByType("중식")).willReturn(mockResult);

        // when & then
        mockMvc.perform(get("/api/stores/type/중식"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.storeDtoList[0].name").value("금성반점"))
                .andExpect(jsonPath("$.storeDtoList[0].type").value("중식"));
    }

    @Test
    @DisplayName("가게 상세 정보를 반환한다.")
    void findStoreByIdTest() throws Exception {

        StoreDto mockStore = StoreDto.builder()
                .storeId(1L)
                .name("테스트 가게")
                .location("서울")
                .type("한식")
                .build();

        given(storeService.findByStoreId(1L)).willReturn(mockStore);

        mockMvc.perform(get("/api/stores/detail/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.storeId").value(1))
                .andExpect(jsonPath("$.name").value("테스트 가게"))
                .andExpect(jsonPath("$.location").value("서울"))
                .andExpect(jsonPath("$.type").value("한식"));
    }

}
