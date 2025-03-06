package com.example.ogiyo.domain.menus.repository;

import com.example.ogiyo.domain.menus.entity.Menu;
import com.example.ogiyo.domain.menus.enums.Status;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    // 가게 ID로 메뉴 조회
    List<Menu> findByStoreId(Long storeId);
    List<Menu> findByStoreIdAndCategory(Long storeId, String category);
    // Store엔티티의 owner 쿼리 가져오기
    @EntityGraph(attributePaths = {"store.owner"})
    Optional<Menu> findWithStoreAndOwnerById(Long menuId);
    List<Menu> findByStoreIdAndStatusNot(Long storeId, Status status);

}
