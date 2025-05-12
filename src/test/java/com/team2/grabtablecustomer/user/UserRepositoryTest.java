package com.team2.grabtablecustomer.user;

import com.team2.grabtablecustomer.domain.user.entity.User;
import com.team2.grabtablecustomer.domain.user.repository.MembershipRepository;
import com.team2.grabtablecustomer.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
@Rollback
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 이메일로_유저_조회() {
        // given
        User user = new User();
        user.setName("테스트");
        user.setEmail("test@example.com");
        user.setPassword("암호"); // 인코딩 안 해도 무관
        user.setMemberships(List.of()); // 비어있어도 됨

        userRepository.save(user);

        // when
        Optional<User> found = userRepository.findByEmail("test@example.com");

        // then
        assertTrue(found.isPresent());
        assertEquals("테스트", found.get().getName());
    }
}