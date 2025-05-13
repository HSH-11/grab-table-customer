package com.team2.grabtablecustomer.domain.menu.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuResultDto {
    private String result;
    private MenuDto menuDto;
    private List<MenuDto> menuDtoList;
    private Long count;
}
