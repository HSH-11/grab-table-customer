package com.team2.grabtablecustomer.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto {
    private Long menuId;
    private Long storeId;
    private String name;
    private int price;

    private byte[] image;
    private String imageContentType;
}
