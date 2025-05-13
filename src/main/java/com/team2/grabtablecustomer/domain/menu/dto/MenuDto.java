package com.team2.grabtablecustomer.domain.menu.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MenuDto {
    private Long menuId;
    private Long storeId;
    private String name;
    private int price;

    private byte[] image;
    private String imageContentType;
}
