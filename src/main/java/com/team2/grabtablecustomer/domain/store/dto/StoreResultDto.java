package com.team2.grabtablecustomer.domain.store.dto;

import lombok.Data;

import java.util.List;

@Data
public class StoreResultDto {
    private String result;
    private StoreDto storeDto;
    private List<StoreDto> storeDtoList;
    private Long count;
}
