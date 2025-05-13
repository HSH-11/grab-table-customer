package com.team2.grabtablecustomer.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StoreImageDto {
    private byte[] data;
    private String contentType;
}
