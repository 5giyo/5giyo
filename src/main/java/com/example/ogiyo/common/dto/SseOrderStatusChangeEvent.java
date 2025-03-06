package com.example.ogiyo.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SseOrderStatusChangeEvent implements OrderStatusChangeEvent {
    private Long userId;
    private Status status;

    void changeStatus(Status status) {
        this.status = status;
    }
}
