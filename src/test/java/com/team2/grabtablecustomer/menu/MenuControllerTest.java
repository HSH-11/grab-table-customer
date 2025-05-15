package com.team2.grabtablecustomer.menu;

import com.team2.grabtablecustomer.config.MyAuthenticationFailureHandler;
import com.team2.grabtablecustomer.config.MyAuthenticationSuccessHandler;
import com.team2.grabtablecustomer.config.SecurityConfig;
import com.team2.grabtablecustomer.domain.menu.controller.MenuController;
import com.team2.grabtablecustomer.domain.menu.dto.MenuDto;
import com.team2.grabtablecustomer.domain.menu.dto.MenuImageDto;
import com.team2.grabtablecustomer.domain.menu.dto.MenuResultDto;
import com.team2.grabtablecustomer.domain.menu.service.MenuService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MenuController.class)
@Import({SecurityConfig.class, MyAuthenticationSuccessHandler.class, MyAuthenticationFailureHandler.class})
public class MenuControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MenuService menuService;

    @Test
    @DisplayName("GET /api/menus/stores/{storeId} - 가게id로 메뉴 조회")
    void findMenusByStoreIdTest() throws Exception {
        MenuDto menu = new MenuDto(1L, 1L, "불고기", 15000, null, null);
        MenuResultDto result = new MenuResultDto();
        result.setResult("success");
        result.setMenuDtoList(List.of(menu));

        given(menuService.findByStoreId(1L)).willReturn(result);

        mockMvc.perform(get("/api/menus/stores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.menuDtoList[0].name").value("불고기"));
    }

    @Test
    @DisplayName("GET /api/menus/{menuId} - 메뉴 단건 조회")
    void findByMenuIdTest() throws Exception {
        MenuDto menuDto = MenuDto.builder()
                .menuId(1L)
                .storeId(10L)
                .name("비빔밥")
                .price(9000)
                .build();

        MenuResultDto resultDto = new MenuResultDto();
        resultDto.setResult("success");
        resultDto.setMenuDto(menuDto);

        given(menuService.findByMenuId(1L)).willReturn(resultDto);

        mockMvc.perform(get("/api/menus/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value("success"))
                .andExpect(jsonPath("$.menuDto.name").value("비빔밥"))
                .andExpect(jsonPath("$.menuDto.price").value(9000));
    }

    @Test
    @DisplayName("GET /api/menus/{menuId}/image - 메뉴 이미지 조회")
    void getMenuImageTest() throws Exception {
        byte[] imageBytes = new byte[]{1, 2, 3, 4}; // dummy image data
        String contentType = "image/jpeg";

        MenuImageDto imageDto = MenuImageDto.builder()
                .data(imageBytes)
                .contentType(contentType)
                .build();

        given(menuService.getMenuImage(1L)).willReturn(imageDto);

        mockMvc.perform(get("/api/menus/1/image"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.contentType").value(contentType))
                .andExpect(jsonPath("$.data").exists());
    }

}
