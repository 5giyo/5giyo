package com.example.ogiyo.domain.order.dto.response;

import com.example.ogiyo.domain.order.entity.OrderStatus;

public class OrderResponseDto {

    private long orderId;
    private String paymentMethod;
    private int totalPrice;
    private OrderStatus status;

    public OrderResponseDto(long orderId, String paymentMethod, int totalPrice, OrderStatus status) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    //보여줄 값 Id,결제수단,총금액,배달상태, 메뉴이름 정도?
}
