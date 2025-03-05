package com.example.ogiyo.domain.order.repository;

import com.example.ogiyo.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
