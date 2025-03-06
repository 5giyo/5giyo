package com.example.ogiyo.domain.order.dto.request;

import com.example.ogiyo.domain.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class UpdateOrderRequestDto {
    private OrderStatus status;
    private int totalPrice;
    private String paymentMethod;
    private int quantity;
}
