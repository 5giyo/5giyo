package com.example.ogiyo.domain.order.service;


import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.exception.NotFoundOrderException;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.cart.dto.response.GetCartResponseDto;
import com.example.ogiyo.domain.cart.service.CartServiceImpl;
import com.example.ogiyo.domain.coupon.service.CouponServiceImpl;
import com.example.ogiyo.domain.menus.entity.Menu;
import com.example.ogiyo.domain.menus.repository.MenuRepository;
import com.example.ogiyo.domain.order.dto.request.RequireOrderRequestDto;
import com.example.ogiyo.domain.order.dto.response.*;
import com.example.ogiyo.domain.order.entity.OrderStatus;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final JwtUtil jwtUtil;
    private final CartServiceImpl cartService;
    private final CouponServiceImpl couponService;
    private final MenuRepository menuRepository;


    //주문요청
    @Override
    @Transactional
    public ResponseDto<RequireOrderResponseDto> requestOrder(String token, RequireOrderRequestDto requireOrderRequestDto) {
        Long memberId = jwtUtil.extractMemberId(token);

        // 장바구니 조회 (cartService.getCart에서 Map<Long, Integer> 반환)
        ResponseDto<GetCartResponseDto> cartResponse = cartService.getCart(memberId);
        Map<Long, Integer> cartItems = cartResponse.getData().getItems();

        BigDecimal getTotalPrice = BigDecimal.ZERO;

        // 장바구니 아이템을 순회하며 총 가격 계산
        for (Map.Entry<Long, Integer> entry : cartItems.entrySet()) {
            Long menuId = entry.getKey();
            Integer quantity = entry.getValue();

            // 메뉴 정보 조회
            Menu menu = menuRepository.findById(menuId)
                    .orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));
            BigDecimal menuPrice = BigDecimal.valueOf(menu.getPrice());

            // 메뉴 가격 * 수량 계산 후 총 가격에 추가
            getTotalPrice = getTotalPrice.add(menuPrice.multiply(BigDecimal.valueOf(quantity)));
        }

        //쿠폰 할인 금액 계산
        BigDecimal discountPrice = getDiscountPriceFromCoupon(requireOrderRequestDto.getCouponCode());

        //주문전 총금액
        BigDecimal orderPrice = getTotalPrice.subtract(discountPrice);

        //주문 생성
        Order newOrder = Order.builder()
                .orderStatus(OrderStatus.REQUIRED)
                .paymentMethod(requireOrderRequestDto.getPaymentMethod())
                .totalPrice(orderPrice)
                .build();

        Order savedOrder = orderRepository.save(newOrder);

        //주문생성 후 장바구니 비우기
        cartService.deleteCard(memberId);


        RequireOrderResponseDto responseDto = new RequireOrderResponseDto(
                savedOrder.getOrderId(),
                savedOrder.getOrderStatus()
        );


        return ResponseDto.success(responseDto);
    }

    //주문 수락하기
    @Transactional
    @Override
    public ResponseDto<AcceptOrderResponseDto> acceptOrder(Long orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(NotFoundOrderException::new);

        order.updateOrder(OrderStatus.PREPARING);
        AcceptOrderResponseDto acceptOrder = new AcceptOrderResponseDto(
                order.getOrderId(),
                OrderStatus.PREPARING
        );

        return ResponseDto.success(acceptOrder);
    }

    //주문 거절하기(사장님) //TODO: 유저롤 집어넣기.
    @Override
    @Transactional
    public ResponseDto<RejectOrderResponseDto> rejectOrder(Long orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(NotFoundOrderException::new);

        order.updateOrder(OrderStatus.REJECTED);
        RejectOrderResponseDto rejectOrder = new RejectOrderResponseDto(
                order.getOrderId(),
                OrderStatus.REJECTED
        );

        return ResponseDto.success(rejectOrder);
    }

    //배달완료
    @Override
    @Transactional
    public ResponseDto<CompleteOrderResponseDto> completeOrder(Long orderId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(NotFoundOrderException::new);

        order.updateOrder(OrderStatus.DELIVERED);
        CompleteOrderResponseDto completeOrder = new CompleteOrderResponseDto(
                order.getOrderId(),
                OrderStatus.DELIVERED
        );

        return ResponseDto.success(completeOrder);
    }

    //주문 취소하기(고객) //TODO: 유저롤 집어넣기.
    @Override
    @Transactional
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
                .findById(orderId)
                .orElseThrow(NotFoundOrderException::new);

        GetOrderResponseDto getOrder = new GetOrderResponseDto(
                order.getOrderId(),
                order.getOrderStatus()
        );

        return ResponseDto.success(getOrder);
    }
    
    //주문 수정하기(주문상태 변경, 결제수단 변경,등)
    @Override
    @Transactional
    public ResponseDto<UpdateOrderResponseDto> updateOrder(Long orderId, OrderStatus orderStatus, String paymentMethod) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(NotFoundOrderException::new);

        order.update(orderStatus, paymentMethod);

        UpdateOrderResponseDto responseDto = new UpdateOrderResponseDto(
                order.getOrderId(),
                order.getOrderStatus(),
                order.getPaymentMethod()
        );

        return ResponseDto.success(responseDto);
    }

    private BigDecimal getDiscountPriceFromCoupon(String couponCode) {
        //쿠폰유효성 검사 및 할인금액 추출
        return couponService.findByCouponCode(couponCode).getDiscountPrice();
    }

    //findOrder entity 반환하는 메서드입니다.
    public Order findOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()->new IllegalArgumentException("찾을수 없는 주문입니다."));
    }
}
