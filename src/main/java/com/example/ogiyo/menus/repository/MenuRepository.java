package com.example.ogiyo.menus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByStore_StoreId(Long storeId);
    List<Menu> findByMenuNameContainingIgnoreCase(String keyword);
}
