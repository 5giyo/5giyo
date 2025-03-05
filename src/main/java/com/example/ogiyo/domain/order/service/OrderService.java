package com.example.ogiyo.domain.order.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.domain.order.dto.response.*;
import com.example.ogiyo.domain.order.dto.request.RequireOrderRequestDto;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.order.entity.OrderStatus;

import java.util.List;


public interface OrderService {

    //주문요청하기
//    ResponseDto<RequireOrderResponseDto> requireOrder(String token, RequireOrderRequestDto requireOrderRequestDto);

    //주문수락하기
    ResponseDto<AcceptOrderResponseDto> acceptOrder(Long orderId);

    //주문 거절하기
    ResponseDto<RejectOrderResponseDto> rejectOrder(Long orderId);

    //주문 전체 조회
    ResponseDto<List<Order>> findAllOrders();

    //주문 단건 조회
    ResponseDto<GetOrderResponseDto> findOrderById(Long orderId);

    //주문 업데이트
    ResponseDto<UpdateOrderResponseDto> updateOrder(Long orderId, OrderStatus orderStatus, String paymentMethod);

    //주문삭제
    ResponseDto<String> deleteOrder(Long orderId);


}
