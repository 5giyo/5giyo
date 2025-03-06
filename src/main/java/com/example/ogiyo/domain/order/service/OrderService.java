package com.example.ogiyo.domain.order.service;

import com.example.ogiyo.domain.order.dto.request.AddOrderRequestDto;
import com.example.ogiyo.domain.order.entity.OrderStatus;


public interface OrderService {

    void  addOrder(Long userId, Long menuId, AddOrderRequestDto addOrderRequestDto);
//    OrderResponseDto
//    List<OrderResponseDto> findAllOrders(Long userId);

    void findOrderById(Long orderId);
//    OrderResponseDto
    void updateOrder(Long id, OrderStatus orderStatus, int quantity);

    void deleteOrder(Long orderId);

    void addCart(Long userId, Long menuId, int quantity);

    void removeCart(Long userId, Long menuId);

    int calculateTotal(Long userId);

}
