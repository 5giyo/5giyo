package com.example.ogiyo.domain.order.dto.response;

import com.example.ogiyo.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AcceptOrderResponseDto {
    private final Long orderId;
    private final OrderStatus orderStatus;
}
