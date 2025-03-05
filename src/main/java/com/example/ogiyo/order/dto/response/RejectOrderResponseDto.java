package com.example.ogiyo.order.dto.response;

import com.example.ogiyo.order.entity.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RejectOrderResponseDto {
    private final Long orderId;
    private final OrderStatus orderStatus;
}
