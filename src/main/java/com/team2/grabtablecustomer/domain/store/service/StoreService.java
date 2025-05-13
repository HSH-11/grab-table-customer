package com.team2.grabtablecustomer.domain.store.service;

import com.team2.grabtablecustomer.domain.store.dto.StoreResultDto;

public interface StoreService {

    StoreResultDto findAll();
    StoreResultDto findByType(String type);
}
