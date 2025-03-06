package com.example.ogiyo.domain.order.dto.request;

import com.example.ogiyo.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateOrderRequestDto {
    private OrderStatus status;
    private String paymentMethod;

}
