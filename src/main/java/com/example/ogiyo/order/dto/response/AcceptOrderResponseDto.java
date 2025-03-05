package com.example.ogiyo.order.dto.response;

import com.example.ogiyo.order.entity.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class AcceptOrderResponseDto {
    private final Long orderId;
    private final OrderStatus orderStatus;
    private LocalDateTime orderDate;
}
