package com.team2.grabtablecustomer.domain.menu.controller;

import com.team2.grabtablecustomer.domain.menu.dto.MenuImageDto;
import com.team2.grabtablecustomer.domain.menu.dto.MenuResultDto;
import com.team2.grabtablecustomer.domain.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MenuController {

    private final MenuService menuService;

    @GetMapping("/stores/{storeId}/menus")
    public ResponseEntity<MenuResultDto> findByStoreId(@PathVariable("storeId") Long storeId) {
        return ResponseEntity.status(200).body(menuService.findByStoreId(storeId));
    }

    @GetMapping("/menus/{menuId}")
    public ResponseEntity<MenuResultDto> findByMenuId(@PathVariable Long menuId) {
        return ResponseEntity.status(200).body(menuService.findByMenuId(menuId));
    }

    @GetMapping("/menus/{menuId}/image")
    public ResponseEntity<MenuImageDto> getMenuImage(@PathVariable Long menuId) {
        return ResponseEntity.ok(menuService.getMenuImage(menuId));
    }
}
