package com.example.ogiyo.domain.menus.repository;

import com.example.ogiyo.domain.menus.entity.OrderCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCountRepository extends JpaRepository<OrderCount, Long> {
    @Modifying
    @Query("UPDATE OrderCount o SET o.orderCount = o.orderCount + 1 WHERE o.orderCountId = :orderCountId")
    void increaseOrderCount(@Param("orderCountId") Long orderCountId);
}

