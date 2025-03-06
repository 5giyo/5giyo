package com.example.ogiyo.domain.menus.repository;

import com.example.ogiyo.domain.menus.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    // 가게 ID로 메뉴 조회
    List<Menu> findByStoreId(Long storeId);

}
