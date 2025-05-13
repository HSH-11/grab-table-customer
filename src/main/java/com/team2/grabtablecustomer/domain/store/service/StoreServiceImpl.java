package com.team2.grabtablecustomer.domain.store.service;


import com.team2.grabtablecustomer.domain.store.dto.StoreDto;
import com.team2.grabtablecustomer.domain.store.dto.StoreResultDto;
import com.team2.grabtablecustomer.domain.store.entity.Store;
import com.team2.grabtablecustomer.domain.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;


    @Override
    public StoreResultDto findAll() {
        StoreResultDto storeResultDto = new StoreResultDto();

        try {
            List<Store> stores = storeRepository.findAll();
            List<StoreDto> storeDtoList = new ArrayList<>();

            for (Store store : stores) {
                StoreDto storeDto = StoreDto.builder()
                        .storeId(store.getStoreId())
                        .name(store.getName())
                        .location(store.getLocation())
                        .type(store.getType())
                        .image(store.getImage())
                        .imageContentType(store.getImageContentType())
                        .build();
                storeDtoList.add(storeDto);
            }

            storeResultDto.setStoreDtoList(storeDtoList);
            storeResultDto.setResult("success");

        } catch (Exception e) {
            e.printStackTrace();
            storeResultDto.setResult("fail");
        }

        return storeResultDto;
    }

    @Override
    public StoreResultDto findByType(String type) {
        StoreResultDto storeResultDto = new StoreResultDto();

        try {
            List<Store> stores = storeRepository.findByType(type);
            List<StoreDto> storeDtoList = new ArrayList<>();

            for (Store store : stores) {
                StoreDto storeDto = StoreDto.builder()
                        .storeId(store.getStoreId())
                        .name(store.getName())
                        .location(store.getLocation())
                        .type(store.getType())
                        .image(store.getImage())
                        .imageContentType(store.getImageContentType())
                        .build();
                storeDtoList.add(storeDto);
            }

            storeResultDto.setStoreDtoList(storeDtoList);
            storeResultDto.setResult("success");

        } catch (Exception e) {
            e.printStackTrace();
            storeResultDto.setResult("fail");
        }

        return storeResultDto;
    }
}
