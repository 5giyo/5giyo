package com.example.ogiyo.order.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.order.dto.response.*;
import com.example.ogiyo.order.dto.request.RequireOrderRequestDto;
import com.example.ogiyo.order.entity.Order;
import com.example.ogiyo.order.entity.OrderStatus;

import java.util.List;


public interface OrderService {

    //주문요청하기
    ResponseDto<RequireOrderResponseDto> requireOrder(String token, RequireOrderRequestDto requireOrderRequestDto);

    //주문수락하기
    ResponseDto<AcceptOrderResponseDto> acceptOrder(Long orderId);

    //주문 거절하기
    ResponseDto<RejectOrderResponseDto> rejectOrder(Long orderId);


    //주문전체조회 고민해보기.
    ResponseDto<List<Order>> findAllOrders();


    ResponseDto<GetOrderResponseDto> findOrderById(Long orderId);

    ResponseDto<UpdateOrderResponseDto> updateOrder(Long orderId, OrderStatus orderStatus, String paymentMethod);

    ResponseDto<String> deleteOrder(Long orderId);

}
