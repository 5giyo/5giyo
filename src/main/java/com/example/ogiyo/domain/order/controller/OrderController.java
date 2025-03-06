package com.example.ogiyo.domain.order.controller;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.etc.JwtProperties;
import com.example.ogiyo.domain.order.dto.request.RequireOrderRequestDto;
import com.example.ogiyo.domain.order.dto.request.UpdateOrderRequestDto;
import com.example.ogiyo.domain.order.dto.response.UpdateOrderResponseDto;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.order.service.OrderServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    //TODO:주문 요청하기 장바구니 기능과 함께 수정 필요함.
    @PostMapping
    public ResponseEntity<ResponseDto<?>> addOrder(
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @Valid @RequestBody RequireOrderRequestDto requireOrderRequestDto
    ) {
        return ResponseEntity.ok(orderServiceImpl.requestOrder(token,requireOrderRequestDto));
    }

    //주문 거절하기
    @PostMapping("/{orderId}/reject")
    public ResponseEntity<ResponseDto<?>> rejectOrder(
            @PathVariable long orderId
    ) {
        return ResponseEntity.ok(orderServiceImpl.rejectOrder(orderId));
    }

    //주문 수락하기
    @PostMapping("/{orderId}/accecpt")
    public ResponseEntity<ResponseDto<?>> AcceptOrder(
            @PathVariable long orderId
    ) {
        return ResponseEntity.ok(orderServiceImpl.acceptOrder(orderId));
    }

    //주문 삭제(취소하기)
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable long orderId) {
        orderServiceImpl.deleteOrder(orderId);
        return ResponseEntity.ok().build();
    }

    //주문전체조회
    @GetMapping()
    public ResponseEntity<List<Order>> findAllOrders(

    ) {
        return ResponseEntity.ok(orderServiceImpl.findAllOrders().getData());
    }

    //주문 단건 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<ResponseDto<?>> findOrder(
            @PathVariable long orderId) {
        return ResponseEntity.ok(orderServiceImpl.findOrderById(orderId));
    }

    //주문수정
    @PatchMapping("/{orderId}")
    public ResponseEntity<UpdateOrderResponseDto> updateOrder(
            @PathVariable Long orderId,
            @Valid @RequestBody UpdateOrderRequestDto dto) {
        orderServiceImpl.updateOrder(
                orderId,dto.getStatus(), dto.getPaymentMethod());

        return ResponseEntity.ok().build();
    }



}
