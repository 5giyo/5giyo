package com.example.ogiyo.menus.repository;

import com.example.ogiyo.menus.entity.OrderCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderCountRepository extends JpaRepository<OrderCount, Long> {
    Optional<OrderCount> findById(Long orderCountId);  // 특정 주문 카운트 조회

    // 주문 횟수 증가
    @Modifying
    @Query("UPDATE OrderCount o SET o.orderCount = o.orderCount + 1 WHERE o.orderCountId = :orderCountId")
    void increaseOrderCount(@Param("orderCountId") Long orderCountId);
}

