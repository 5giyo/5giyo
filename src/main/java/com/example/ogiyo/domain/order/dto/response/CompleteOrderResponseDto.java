package com.example.ogiyo.domain.order.dto.response;

import com.example.ogiyo.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CompleteOrderResponseDto {
    private final Long orderId;
    private final OrderStatus orderStatus;
}
