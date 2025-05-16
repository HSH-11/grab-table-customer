package com.team2.grabtablecustomer.menu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.grabtablecustomer.domain.menu.dto.MenuDto;
import com.team2.grabtablecustomer.domain.menu.entity.Menu;
import com.team2.grabtablecustomer.domain.menu.repository.MenuRepository;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Rollback
public class MenuIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private StoreRepository storeRepository;


    @Test
    @DisplayName("storeId로 메뉴 목록 조회")
    void testFindMenusByStoreId() throws Exception {

        Store store = Store.builder()
                .name("통합테스트가게")
                .location("서울")
                .type("한식")
                .build();
        storeRepository.save(store);

        Menu menu = Menu.builder()
                .name("비빔밥")
                .price(12000)
                .store(store)
                .image("테스트 이미지".getBytes())
                .imageContentType("image/jpeg")
                .build();
        menuRepository.save(menu);

        mockMvc.perform(get("/api/menus/stores/" + store.getStoreId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.menuDtoList[0].name").value("비빔밥"))
                .andExpect(jsonPath("$.menuDtoList[0].price").value(12000));
    }

    @Test
    @DisplayName("menuId로 단일 메뉴 조회")
    void testFindMenuByMenuId() throws Exception {
        Store store = Store.builder()
                .name("단일메뉴가게")
                .location("부산")
                .type("일식")
                .build();
        storeRepository.save(store);

        Menu menu = Menu.builder()
                .name("초밥")
                .price(18000)
                .store(store)
                .image("이미지".getBytes())
                .imageContentType("image/png")
                .build();
        menuRepository.save(menu);


        mockMvc.perform(get("/api/menus/" + menu.getMenuId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.menuDto.name").value("초밥"))
                .andExpect(jsonPath("$.menuDto.price").value(18000));
    }

    @Test
    @DisplayName("menuId로 메뉴 이미지 조회")
    void testGetMenuImage() throws Exception {

        Store store = Store.builder()
                .name("이미지메뉴가게")
                .location("대전")
                .type("중식")
                .build();
        storeRepository.save(store);

        byte[] imageData = "image data".getBytes();

        Menu menu = Menu.builder()
                .name("짜장면")
                .price(9000)
                .store(store)
                .image(imageData)
                .imageContentType("image/jpeg")
                .build();
        menuRepository.save(menu);

        mockMvc.perform(get("/api/menus/" + menu.getMenuId() + "/image"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.contentType").value("image/jpeg"));
    }
}
