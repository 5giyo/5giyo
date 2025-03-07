package com.example.ogiyo.common.util;

/*
 * 주문의 상태에 따라 발생하는 이벤트
 * 각 상태별로 클라이언트에게 알림을 보내주는 기능
 * */


import com.example.ogiyo.common.dto.OrderStatusChangeEvent;

public interface OrderStatusChangeEventListener {

    void sendNotification(OrderStatusChangeEvent event);
}
