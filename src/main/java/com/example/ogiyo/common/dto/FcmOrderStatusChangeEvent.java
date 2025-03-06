package com.example.ogiyo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FcmOrderStatusChangeEvent implements OrderStatusChangeEvent {
    private Long orderId;
    private Status status;
}
