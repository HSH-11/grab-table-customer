package com.team2.grabtablecustomer.domain.store.controller;

import com.team2.grabtablecustomer.domain.store.dto.StoreResultDto;
import com.team2.grabtablecustomer.domain.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<StoreResultDto> findAll() {
        return ResponseEntity.status(200).body(storeService.findAll());
    }

    @GetMapping("/{searchType}")
    public ResponseEntity<StoreResultDto> findByType(@PathVariable String searchType) {
        return ResponseEntity.status(200).body(storeService.findByType(searchType));
    }

}
