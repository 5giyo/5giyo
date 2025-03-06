package com.example.ogiyo.common.dto;

import lombok.Getter;

public interface OrderStatusChangeEvent {
    Status getStatus();

    @Getter
    enum Status {
        ORDERED("주문 완료 되었습니다."),
        PREPARING("음식 준비 중 입니다."),
        DELIVERING("배달 중 입니다."),
        DELIVERED("배달 완료 되었습니다."),
        CANCELED("주문 취소 되었습니다.");

        private final String message;

        Status(String message) {
            this.message = message;
        }
    }
}
