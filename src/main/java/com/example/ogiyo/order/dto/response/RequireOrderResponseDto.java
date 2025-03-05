package com.example.ogiyo.order.dto.response;

import com.example.ogiyo.order.entity.OrderStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RequireOrderResponseDto {

    private final Long orderId;
    private OrderStatus orderStatus;

    public RequireOrderResponseDto(Long orderId, OrderStatus orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    //보여줄 값 Id,결제수단,총금액,배달상태, 메뉴이름 정도?
    public static RequireOrderResponseDto fromOrder(Long orderId, OrderStatus orderStatus) {
        return new RequireOrderResponseDto(orderId,orderStatus);

    }
}
