package com.example.ogiyo.order.service;

import com.example.ogiyo.order.dto.request.AddOrderRequestDto;
import com.example.ogiyo.order.dto.response.OrderResponseDto;
import com.example.ogiyo.order.entity.OrderStatus;
import com.example.ogiyo.order.entity.Order;
import com.example.ogiyo.order.repository.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
//    private final MenuRepository menuRepository;
//    private final UserRepository userRepository;
    private final RedisTemplate<Long, Order> redisTemplate;


    /*
    * 메뉴에 있는것.
    * 메뉴이름
    * 가격
    * 상태
    * */
    //주문하기 상태 배달중으로 변경.
    /*
    * 주문할때 필요한것
    * 가격과 수량
    * 상태(매진인지 아닌지) 매진이라면 예외처리
    * */
    @Override
    public OrderResponseDto addOrder(Long userId, Long menuId, AddOrderRequestDto addOrderRequestDto) {
        User user =


        Menu menu = menuRepository.findById(menuId).orElseThrow(()->
                new InvalidRequestException("메뉴를 찾을 수 없습니다."));

        if (!menu.getStatus()) {
            throw new RuntimeException("판매중인 상품이 아닙니다.");
        }

        Order newOrder = createOrder(addOrderRequestDto, menu);
        Order savedOrder = orderRepository.save(newOrder);
        cacheOrder(savedOrder); //24시간 TTL 설정.

        return OrderResponseDto.toDto(savedOrder);
    }

    //주문 전체 조회하기
    @Override
    @Transactional(readOnly = true)
    public List<OrderResponseDto> findAllOrders(Long userId) {

        return orderRepository.findAll().
                stream().map(OrderResponseDto::toDto).collect(Collectors.toList());
    }

    //주문 단건 조회하기
    @Override
    public OrderResponseDto findOrderById(Long orderId) {
        return null;
    }

    //주문 수정하기(주문상태 변경, 결제수단 변경,등)
    @Override
    public void updateOrder(Long orderId, OrderStatus orderStatus, String paymentMethod, int quantity){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()->new RuntimeException("찾을 수 없는 주문입니다."));

        order.update(paymentMethod,
                orderStatus,
                quantity
        );
    }

    //주문 취소하기
    @Override
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }



    //장바구니에 메뉴 추가하기


    //장바구니에 메뉴 삭제하기

    //장바구니 메뉴 수정하기

    //장바구니 조회하기

    //장바구니 구현 후 장바구니에 있는 물건들의 총 가격합 계산하는 메서드
    private int getTotalPrice(Long orderId) {
        Order order = orderRepository.findById(orderId).get();
        return order.getTotalPrice();
    }

    private Order createOrder(AddOrderRequestDto addOrderRequestDto, Menu menu) {
        return new Order(
                addOrderRequestDto.getPaymentMethod(),
                menu.getPrice(),
                OrderStatus.DELIVERING
        );
    }

    private Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    private void cacheOrder(Order order) {
        redisTemplate.opsForValue().set(order.getOrderId(), order,24, TimeUnit.HOURS);
    }
}
