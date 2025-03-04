package com.example.ogiyo.order.service;

import com.example.ogiyo.order.dto.request.AddCartRequestDto;
import com.example.ogiyo.order.dto.request.AddOrderRequestDto;
import com.example.ogiyo.order.dto.response.OrderResponseDto;
import com.example.ogiyo.order.entity.OrderStatus;

import java.util.List;


public interface OrderService {

    OrderResponseDto addOrder(Long userId, Long menuId, AddOrderRequestDto addOrderRequestDto);

    List<OrderResponseDto> findAllOrders(Long userId);

    OrderResponseDto findOrderById(Long orderId);

    void updateOrder(Long id, OrderStatus orderStatus, String paymentMethod, int quantity);

    void deleteOrder(Long orderId);

    void addCart(Long userId, Long menuId, int quantity);

    void removeCart(Long userId, Long menuId);

    int calculateTotal(Long userId);

}
