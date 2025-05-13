package com.team2.grabtablecustomer.domain.menu.service;

import com.team2.grabtablecustomer.domain.menu.dto.MenuDto;
import com.team2.grabtablecustomer.domain.menu.dto.MenuImageDto;
import com.team2.grabtablecustomer.domain.menu.dto.MenuResultDto;
import com.team2.grabtablecustomer.domain.menu.entity.Menu;
import com.team2.grabtablecustomer.domain.menu.repository.MenuRepository;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    @Override
    public MenuResultDto findByStoreId(Long storeId) {
        MenuResultDto menuResultDto = new MenuResultDto();

        try {

            Optional<Store> optionalStore = storeRepository.findById(storeId);

            if (optionalStore.isPresent()) {
                List<Menu> menuList = menuRepository.findByStoreId(storeId);
                List<MenuDto> menuDtoList = new ArrayList<>();
                for (Menu menu : menuList) {
                    MenuDto menuDto = MenuDto.builder()
                            .menuId(menu.getMenuId())
                            .storeId(storeId)
                            .name(menu.getName())
                            .price(menu.getPrice())
                            .image(menu.getImage())
                            .imageContentType(menu.getImageContentType())
                            .build();
                    menuDtoList.add(menuDto);
                }
                menuResultDto.setMenuDtoList(menuDtoList);
                menuResultDto.setResult("success");

            } else {
                menuResultDto.setResult("notfound");
            }

        } catch (Exception e) {
            e.printStackTrace();
            menuResultDto.setResult("fail");
        }

        return menuResultDto;
    }

    @Override
    public MenuResultDto findByMenuId(Long menuId) {
        MenuResultDto menuResultDto = new MenuResultDto();

        try {

            Optional<Menu> optionalMenu = menuRepository.findById(menuId);

            if (optionalMenu.isPresent()) {
                Menu menu = optionalMenu.get();
                MenuDto menuDto = MenuDto.builder()
                        .menuId(menu.getMenuId())
                        .storeId(menu.getStore().getStoreId())
                        .name(menu.getName())
                        .price(menu.getPrice())
                        .image(menu.getImage())
                        .imageContentType(menu.getImageContentType())
                        .build();
                menuResultDto.setMenuDto(menuDto);
                menuResultDto.setResult("success");
            } else {
                menuResultDto.setResult("notfound");
            }

        } catch (Exception e) {
            e.printStackTrace();
            menuResultDto.setResult("fail");
        }

        return menuResultDto;
    }

    @Override
    public MenuImageDto getMenuImage(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new RuntimeException("menu not found"));
        return new MenuImageDto(menu.getImage(), menu.getImageContentType());
    }
}
