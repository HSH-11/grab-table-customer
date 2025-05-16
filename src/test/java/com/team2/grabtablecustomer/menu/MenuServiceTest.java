package com.team2.grabtablecustomer.menu;

import com.team2.grabtablecustomer.domain.menu.dto.MenuImageDto;
import com.team2.grabtablecustomer.domain.menu.dto.MenuResultDto;
import com.team2.grabtablecustomer.domain.menu.entity.Menu;
import com.team2.grabtablecustomer.domain.menu.repository.MenuRepository;
import com.team2.grabtablecustomer.domain.menu.service.MenuService;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private MenuService menuService;

    @Test
    @DisplayName("가게 ID로 메뉴 리스트 조회 성공")
    void findByStoreId_success() {
        Long storeId = 1L;

        Store store = Store.builder()
                .storeId(storeId)
                .name("한식당")
                .build();

        Menu menu = Menu.builder()
                .menuId(10L)
                .store(store)
                .name("비빔밥")
                .price(8000)
                .build();

        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));
        when(menuRepository.findByStoreId(storeId)).thenReturn(List.of(menu));

        MenuResultDto resultDto = menuService.findByStoreId(storeId);

        assertThat(resultDto.getResult()).isEqualTo("success");
        assertThat(resultDto.getMenuDtoList()).hasSize(1);
        assertThat(resultDto.getMenuDtoList().get(0).getName()).isEqualTo("비빔밥");
    }


    @Test
    @DisplayName("메뉴 ID로 단일 메뉴 조회 성공")
    void findByMenuId_success() {
        Store store = Store.builder().storeId(1L).build();
        Menu menu = Menu.builder()
                .menuId(10L)
                .store(store)
                .name("된장찌개")
                .price(7000)
                .build();

        when(menuRepository.findById(10L)).thenReturn(Optional.of(menu));

        MenuResultDto resultDto = menuService.findByMenuId(10L);

        assertThat(resultDto.getResult()).isEqualTo("success");
        assertThat(resultDto.getMenuDto().getName()).isEqualTo("된장찌개");
    }

    @Test
    @DisplayName("메뉴 ID로 이미지 조회 성공")
    void getMenuImage_success() {
        byte[] imageBytes = new byte[]{1, 2, 3};
        Menu menu = Menu.builder()
                .menuId(1L)
                .image(imageBytes)
                .imageContentType("image/png")
                .build();

        when(menuRepository.findById(1L)).thenReturn(Optional.of(menu));

        MenuImageDto dto = menuService.getMenuImage(1L);

        assertThat(dto.getData()).isEqualTo(imageBytes);
        assertThat(dto.getContentType()).isEqualTo("image/png");
    }

}
