package com.team2.grabtablecustomer.domain.user.repository;

import com.team2.grabtablecustomer.domain.user.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Membership findByName(String name);
}
