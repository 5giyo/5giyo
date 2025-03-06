package com.example.ogiyo.domain.order.service;


import com.example.ogiyo.common.dto.ResponseDto;
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
    public ResponseDto<RequireOrderResponseDto> requestOrder(String token, RequireOrderRequestDto requireOrderRequestDto) {
        Long memberId = jwtUtil.extractMemberId(token);

        ResponseDto<GetCartResponseDto> cartResponse = cartService.getCart(memberId);

        BigDecimal getTotalPrice = new BigDecimal(0);
        //장바구니 안의 아이템들을 갖고옴. 이안에 메뉴Id가 있는데
        for(int i =0; i < cartResponse.getData().getItems().size(); i++) {
            Long menuId = cartResponse.getData().getItems().get(i).getMenuId();
            Menu menu = menuRepository.findById(menuId)
                    .orElseThrow(()-> new IllegalArgumentException("메뉴를 찾을 수 없습니다."));
            BigDecimal menuPrice = BigDecimal.valueOf(menu.getPrice());
            getTotalPrice = getTotalPrice.add(menuPrice);
        }

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
                .orElseThrow(()->new IllegalArgumentException("주문을 찾지 못했습니다."));

        AcceptOrderResponseDto acceptOrder = new AcceptOrderResponseDto(
                order.getOrderId(),
                OrderStatus.PREPARING
        );

        return ResponseDto.success(acceptOrder);
    }

    //주문 거절하기(사장님) //TODO: 유저롤 집어넣기.
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


    //주문 취소하기(고객) //TODO: 유저롤 집어넣기.
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
                order.getOrderId(),
                order.getOrderStatus()
        );

        return ResponseDto.success(getOrder);
    }

    //TODO: 배달완료



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

    //
    private BigDecimal getDiscountPriceFromCoupon(String couponCode) {
        //쿠폰유효성 검사 및 할인금액 추출
        return couponService.findByCouponCode(couponCode).getDiscountPrice();
    }

    //findOrder entity 반환하는 메서드입니다.
    public Order findOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(()->new IllegalArgumentException("찾을수 없는 주문입니다."));
    }
}
