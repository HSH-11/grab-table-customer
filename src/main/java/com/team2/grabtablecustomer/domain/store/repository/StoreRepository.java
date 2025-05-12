package com.team2.grabtablecustomer.domain.store.repository;

import com.team2.grabtablecustomer.domain.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
