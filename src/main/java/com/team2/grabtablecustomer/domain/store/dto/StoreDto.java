package com.team2.grabtablecustomer.domain.store.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreDto {
    private Long storeId;
    private Long ownerId;
    private String name;
    private String location;
    private String type;

    private byte[] image;
    private String imageContentType;
}
