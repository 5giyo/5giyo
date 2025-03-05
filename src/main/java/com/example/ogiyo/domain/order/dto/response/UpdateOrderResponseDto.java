package com.example.ogiyo.domain.order.dto.response;

import com.example.ogiyo.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UpdateOrderResponseDto {
    private final long orderId;
    private final OrderStatus orderStatus;
    private final String paymentMethod;
}
