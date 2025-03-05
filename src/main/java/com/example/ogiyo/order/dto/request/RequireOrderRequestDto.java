package com.example.ogiyo.order.dto.request;

import com.example.ogiyo.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequireOrderRequestDto {
    private String token;
    private OrderStatus orderStatus;
    private String paymentMethod;

}
