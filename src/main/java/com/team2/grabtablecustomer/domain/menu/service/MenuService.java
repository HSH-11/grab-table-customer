package com.team2.grabtablecustomer.domain.menu.service;

import com.team2.grabtablecustomer.domain.menu.dto.MenuImageDto;
import com.team2.grabtablecustomer.domain.menu.dto.MenuResultDto;

public interface MenuService {

    MenuResultDto findByStoreId(Long storeId);
    MenuResultDto findByMenuId(Long menuId);
    MenuImageDto getMenuImage(Long menuId);

}
