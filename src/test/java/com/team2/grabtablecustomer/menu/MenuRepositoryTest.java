package com.team2.grabtablecustomer.menu;

import com.team2.grabtablecustomer.domain.menu.entity.Menu;
import com.team2.grabtablecustomer.domain.menu.repository.MenuRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;

@DataJpaTest
@Transactional
@Rollback
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MenuRepositoryTest {

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @DisplayName("storeId로 메뉴 목록 조회")
    void testFindByStoreId() {
        Long storeId = 5L;
        List<Menu> menuList = menuRepository.findByStoreId(storeId);

        assertThat(menuList).isNotEmpty();
        assertThat(menuList.get(0).getName()).isEqualTo("짜장면");
        assertThat(menuList.get(0).getStore().getName()).isEqualTo("금성반점");
    }
}
