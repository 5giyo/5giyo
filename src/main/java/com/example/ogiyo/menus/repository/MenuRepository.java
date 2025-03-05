package com.example.ogiyo.menus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

    List<Menu> findByStore_StoreId(Long storeId); //가게별 메뉴 조회

    // 메뉴 검색
    List<Menu> findByMenuNameContainingIgnoreCase(String menuName);

    @Modifying
    @Query("UPDATE Menu m SET m.searchCount = m.searchCount + 1 WHERE m.menuId = :menuId")
    void incrementSearchCount(@Param("menuId") Long menuId); // 검색될때 검색 횟수 증가
}

