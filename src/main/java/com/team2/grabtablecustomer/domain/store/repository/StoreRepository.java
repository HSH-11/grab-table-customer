package com.team2.grabtablecustomer.domain.store.repository;

import com.team2.grabtablecustomer.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    List<Store> findByType(String type);
}
