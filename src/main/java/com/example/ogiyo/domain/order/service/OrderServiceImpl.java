package com.example.ogiyo.domain.order.service;


import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.cart.service.CartServiceImpl;
import com.example.ogiyo.domain.order.dto.request.RequireOrderRequestDto;
import com.example.ogiyo.domain.order.dto.response.*;
import com.example.ogiyo.domain.order.entity.OrderStatus;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final JwtUtil jwtUtil;
    private final CartServiceImpl cartService;


//    //TODO: 주문요청하기는 메뉴와 함께 해결해보기. 장바구니도 같이!
//    @Transactional
//    @Override
//    public ResponseDto<RequireOrderResponseDto> requireOrder(String token, RequireOrderRequestDto requireOrderRequestDto) {
//        //주문하는법
//        Long memberId = jwtUtil.extractUserId(token);
//        //유저 검증? 구현하고,
//        //2.주문을 신청하고 저장한다.
//        Order savedOrder = orderRepository.save(newOrder);
//
//
//        return ResponseDto.success(RequireOrderResponseDto.toDto(savedOrder));
//    }

    //주문 수락하기
    @Transactional
    @Override
    public ResponseDto<AcceptOrderResponseDto> acceptOrder(Long orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(()->new IllegalArgumentException("주문을 찾지 못했습니다."));

        AcceptOrderResponseDto acceptOrder = new AcceptOrderResponseDto(
                order.getOrderId(),
                OrderStatus.PREPARING
        );

        return ResponseDto.success(acceptOrder);
    }

    //주문 거절하기
    @Override
    public ResponseDto<RejectOrderResponseDto> rejectOrder(Long orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(()->new IllegalArgumentException("주문을 찾지 못했습니다."));

        RejectOrderResponseDto rejectOrder = new RejectOrderResponseDto(
                order.getOrderId(),
                OrderStatus.REJECTED
        );

        return ResponseDto.success(rejectOrder);
    }


    //주문 취소하기
    @Override
    public ResponseDto<String> deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
        return ResponseDto.success("주문이 삭제되었습니다.");
    }


    //주문 전체 조회하기
    @Override
    @Transactional(readOnly = true)
    public ResponseDto<List<Order>> findAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return ResponseDto.success(orders);
    }

    //주문 단건 조회하기
    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetOrderResponseDto> findOrderById(Long orderId) {
        Order order = orderRepository
                .findById(orderId).orElseThrow(()->new IllegalArgumentException("주문을 찾지 못했습니다."));

        GetOrderResponseDto getOrder = new GetOrderResponseDto(
                //TODO:요청시각 넣고싶음.
                order.getOrderId(),
                order.getOrderStatus()
        );

        return ResponseDto.success(getOrder);
    }

    //주문 수정하기(주문상태 변경, 결제수단 변경,등)
    @Override
    public ResponseDto<UpdateOrderResponseDto> updateOrder(Long orderId, OrderStatus orderStatus, String paymentMethod) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new IllegalArgumentException("수정할 주문을 찾을 수 없습니다."));

        order.update(orderStatus, paymentMethod);

        UpdateOrderResponseDto responseDto = new UpdateOrderResponseDto(
                order.getOrderId(),
                order.getOrderStatus(),
                order.getPaymentMethod()
        );

        return ResponseDto.success(responseDto);
    }

    //배송완료



    //findOrder entity 반환하는 메서드입니다.
    public Order findOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("찾을수 없는 주문입니다."));
    }
}
