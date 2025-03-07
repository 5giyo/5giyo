package com.example.ogiyo.common.util;

import com.example.ogiyo.common.dto.OrderStatusChangeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FcmOrderStatusChangeEventListener implements OrderStatusChangeEventListener {

    @Override
    @EventListener(FcmOrderStatusChangeEventListener.class)
    public void sendNotification(OrderStatusChangeEvent event) {

    }
}
