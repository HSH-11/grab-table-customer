package com.team2.grabtablecustomer.user;

import com.team2.grabtablecustomer.domain.user.entity.Membership;
import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.MembershipRepository;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@Rollback // 테스트 후 DB 롤백됨 (운영 DB에서도 안전)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testFindByEmail() {
        Membership membership = new Membership();
        membership.setName("GOLD");
        entityManager.persist(membership);

        User user = User.builder()
                .name("홍길동")
                .email("user@test.com")
                .password("1234")
                .memberships(List.of(membership))
                .build();

        entityManager.persist(user);

        Optional<User> result = userRepository.findByEmail("user@test.com");

        assertTrue(result.isPresent());
        assertEquals("홍길동", result.get().getName());
        assertEquals("GOLD", result.get().getMemberships().get(0).getName());
    }

}


