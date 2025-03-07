package com.example.ogiyo.domain.order.dto.request;

import com.example.ogiyo.domain.order.entity.OrderStatus;
import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequireOrderRequestDto {
    private String token;
    private OrderStatus orderStatus;
    private String paymentMethod;
    @Nullable
    private String couponCode;

    public RequireOrderRequestDto(String couponCode, String paymentMethod) {
        this.couponCode = couponCode;
        this.paymentMethod = paymentMethod;
    }
}
