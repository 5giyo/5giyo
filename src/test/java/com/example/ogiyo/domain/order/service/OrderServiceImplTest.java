package com.example.ogiyo.domain.order.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.cart.dto.response.GetCartItemResponseDto;
import com.example.ogiyo.domain.cart.dto.response.GetCartResponseDto;
import com.example.ogiyo.domain.cart.service.CartServiceImpl;
import com.example.ogiyo.domain.coupon.entity.Coupon;
import com.example.ogiyo.domain.coupon.service.CouponServiceImpl;
import com.example.ogiyo.domain.menus.entity.Menu;
import com.example.ogiyo.domain.menus.repository.MenuRepository;
import com.example.ogiyo.domain.order.dto.request.RequireOrderRequestDto;
import com.example.ogiyo.domain.order.dto.response.RequireOrderResponseDto;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.order.entity.OrderStatus;
import com.example.ogiyo.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private CartServiceImpl cartService;
    @Mock
    private CouponServiceImpl couponService;
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order testOrder;
    private RequireOrderRequestDto testRequestDto;


    @Test
    void requestOrder_성공() {
        // Given: 테스트를 위한 사전 조건 설정
        String token = "testToken";
        Long memberId = 1L;
        String couponCode = "DISCOUNT10";
        BigDecimal menuPrice = BigDecimal.valueOf(10000);
        BigDecimal discountPrice = BigDecimal.valueOf(2000);

        RequireOrderRequestDto requestDto = new RequireOrderRequestDto(couponCode, "CARD");

        // 장바구니 아이템 설정
        ArrayList<GetCartItemResponseDto> cartItems = new ArrayList<>();
        cartItems.add(new GetCartItemResponseDto(1L));
        GetCartResponseDto cartResponse = new GetCartResponseDto(cartItems, 1);

        Menu menu = Menu.builder()
                .store(null)
                .category("한식")
                .price((int) menuPrice.doubleValue())
                .build();


        Order savedOrder = Order.builder()
                .orderId(1L)
                .orderStatus(OrderStatus.REQUIRED)
                .totalPrice(menuPrice.subtract(discountPrice))
                .build();

        // Mock Coupon 객체 생성 (빌더 패턴 사용)
        Coupon mockCoupon = Coupon.builder()
                .couponCode(couponCode)
                .discountPrice(discountPrice)
                .maxDiscountPrice(BigDecimal.ZERO) // 예시 값
                .minDeliveryPrice(BigDecimal.ZERO) // 예시 값
                .isUsed(false) // 예시 값
                .build();


        // Mock 동작 정의
        given(jwtUtil.extractMemberId(token)).willReturn(memberId);
        given(cartService.getCart(memberId)).willReturn(ResponseDto.success(cartResponse));
        given(menuRepository.findById(1L)).willReturn(Optional.of(menu));
        given(couponService.findByCouponCode(couponCode)).willReturn(mockCoupon);
        given(orderRepository.save(any(Order.class))).willReturn(savedOrder);

        // When: 실제 메소드 호출
        ResponseDto<RequireOrderResponseDto> response = orderService.requestOrder(token, requestDto);

        // Then: 결과 검증
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getData());
        assertEquals(savedOrder.getOrderId(), response.getData().getOrderId());
        assertEquals(OrderStatus.REQUIRED, response.getData().getOrderStatus());

        verify(jwtUtil).extractMemberId(token);
        verify(cartService).getCart(memberId);
        verify(menuRepository).findById(1L);
        verify(couponService).findByCouponCode(couponCode);
        verify(orderRepository).save(any(Order.class));
    }
}