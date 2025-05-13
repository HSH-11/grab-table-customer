package com.team2.grabtablecustomer.domain.review.repository;

import com.team2.grabtablecustomer.domain.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("select r from Review r where r.store.storeId = :storeId")
    List<Review> findByStoreId(@Param("storeId") Long storeId);

    @Query("select r from Review r where r.menu.menuId = :menuId")
    List<Review> findByMenuId(@Param("menuId") Long menuId);

    @Query("select r from Review r where r.user.userId = :userId")
    List<Review> findByUserId(@Param("userId") Long userId);
}
