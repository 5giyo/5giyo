package com.example.ogiyo.menus.repository;

import com.example.ogiyo.menus.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    // 가게 ID로 메뉴 조회
    List<Menu> findByStore_StoreId(Long storeId);

}
