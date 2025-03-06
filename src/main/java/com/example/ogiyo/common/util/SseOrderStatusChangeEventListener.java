package com.example.ogiyo.common.util;

import com.example.ogiyo.common.dto.OrderStatusChangeEvent;
import com.example.ogiyo.common.dto.SseOrderStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SseOrderStatusChangeEventListener implements OrderStatusChangeEventListener {

    @Override
    @EventListener(SseOrderStatusChangeEvent.class)
    public void sendNotification(OrderStatusChangeEvent event) {
        log.info("현재 상테: {}", event.getStatus().getMessage());
        // 유저에게 알림을 보내는 기능 추가
    }
}
