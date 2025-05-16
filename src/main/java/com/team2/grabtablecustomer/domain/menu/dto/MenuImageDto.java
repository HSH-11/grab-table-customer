package com.team2.grabtablecustomer.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class MenuImageDto {
    private byte[] data;
    private String contentType;
}
