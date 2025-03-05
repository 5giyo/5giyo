package com.example.ogiyo.order.service;

import com.example.ogiyo.order.dto.request.AddCartRequestDto;
import com.example.ogiyo.order.dto.request.AddOrderRequestDto;
import com.example.ogiyo.order.dto.response.OrderResponseDto;
import com.example.ogiyo.order.entity.OrderStatus;

import java.util.List;


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
