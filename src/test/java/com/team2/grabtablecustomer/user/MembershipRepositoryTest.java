package com.team2.grabtablecustomer.user;

import com.team2.grabtablecustomer.domain.user.entity.Membership;
import com.team2.grabtablecustomer.domain.user.repository.MembershipRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback
class MembershipRepositoryTest {

    @Autowired
    private MembershipRepository membershipRepository;

    @Test
    void testFindByNameSuccess() {
        Membership membership = new Membership();
        membership.setName("다이아");

        membershipRepository.save(membership);

        Membership result = membershipRepository.findByName("다이아");

        assertNotNull(result);
        assertEquals("다이아", result.getName());
    }

}

