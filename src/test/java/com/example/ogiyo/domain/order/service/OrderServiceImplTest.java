package com.example.ogiyo.domain.order.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.cart.dto.response.GetCartResponseDto;
import com.example.ogiyo.domain.cart.service.CartServiceImpl;
import com.example.ogiyo.domain.coupon.entity.Coupon;
import com.example.ogiyo.domain.coupon.service.CouponServiceImpl;
import com.example.ogiyo.domain.menus.entity.Menu;
import com.example.ogiyo.domain.menus.repository.MenuRepository;
import com.example.ogiyo.domain.order.dto.request.RequireOrderRequestDto;
import com.example.ogiyo.domain.order.dto.response.*;
import com.example.ogiyo.domain.order.entity.Order;
import com.example.ogiyo.domain.order.entity.OrderStatus;
import com.example.ogiyo.domain.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

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

        // 장바구니 아이템 설정 (Map<Long, Integer> 형태로 변경)
        Map<Long, Integer> cartItems = Map.of(1L, 2); // 메뉴 ID 1번, 수량 2
        GetCartResponseDto cartResponse = new GetCartResponseDto(cartItems, 2); // 총 수량 2
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
                .status("ACTIVATE") // 예시 값
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

    @Test
    void acceptOrder_ShouldUpdateOrderStatusToPreparing() {
        // Given
        Long orderId = 1L;

        // Create mock Order object using Builder pattern
        Order mockOrder = Order.builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.REQUIRED) // 초기 상태 설정
                .paymentMethod("Cash") // 초기 결제 수단 설정
                .totalPrice(BigDecimal.valueOf(10000)) // 초기 가격 설정
                .build();

        given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));

        // When
        ResponseDto<AcceptOrderResponseDto> response = orderService.acceptOrder(orderId);

        // Then
        assertTrue(response.getSuccess());
        assertEquals(OrderStatus.PREPARING, response.getData().getOrderStatus());
        verify(orderRepository).findById(orderId);
    }


    @Test
    void rejectOrder_ShouldUpdateOrderStatusToRejected() {
        // Given
        Long orderId = 1L;

        // Create mock Order object using Builder pattern
        Order mockOrder = Order.builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.REQUIRED) // 초기 상태 설정
                .paymentMethod("Cash") // 초기 결제 수단 설정
                .totalPrice(BigDecimal.valueOf(10000)) // 초기 가격 설정
                .build();

        given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));

        // When
        ResponseDto<RejectOrderResponseDto> response = orderService.rejectOrder(orderId);

        // Then
        assertTrue(response.getSuccess());
        assertEquals(OrderStatus.REJECTED, response.getData().getOrderStatus());
        verify(orderRepository).findById(orderId);
    }

    @Test
    void completeOrder_ShouldUpdateOrderStatusToDelivered() {
        // Given
        Long orderId = 1L;

        // Create mock Order object using Builder pattern
        Order mockOrder = Order.builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.PREPARING) // 초기 상태 설정
                .paymentMethod("Card") // 초기 결제 수단 설정
                .totalPrice(BigDecimal.valueOf(10000)) // 초기 가격 설정
                .build();

        given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));

        // When
        ResponseDto<CompleteOrderResponseDto> response = orderService.completeOrder(orderId);

        // Then
        assertTrue(response.getSuccess());
        assertEquals(OrderStatus.DELIVERED, response.getData().getOrderStatus());
        verify(orderRepository).findById(orderId);
    }


    @Test
    void deleteOrder_ShouldDeleteOrderSuccessfully() {
        // Given
        Long orderId = 1L;

        // When
        ResponseDto<String> response = orderService.deleteOrder(orderId);

        // Then
        assertTrue(response.getSuccess());
        assertEquals("주문이 삭제되었습니다.", response.getData());
        verify(orderRepository).deleteById(orderId);
    }

    @Test
    void findAllOrders_ShouldReturnAllOrders() {
        // Given
        List<Order> mockOrders = Arrays.asList(
                Order.builder().orderId(1L).build(),
                Order.builder().orderId(2L).build()
        );

        given(orderRepository.findAll()).willReturn(mockOrders);

        // When
        ResponseDto<List<Order>> response = orderService.findAllOrders();

        // Then
        assertTrue(response.getSuccess());
        assertEquals(mockOrders, response.getData());
        verify(orderRepository).findAll();
    }


    @Test
    void findOrderById_ShouldReturnOrderWhenExists() {
        // Given
        Long orderId = 1L;

        // Create mock Order object using Builder pattern
        Order mockOrder = Order.builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.PREPARING) // 초기 상태 설정
                .paymentMethod("Card") // 초기 결제 수단 설정
                .totalPrice(BigDecimal.valueOf(10000)) // 초기 가격 설정
                .build();

        given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));

        // When
        ResponseDto<GetOrderResponseDto> response = orderService.findOrderById(orderId);

        // Then
        assertTrue(response.getSuccess());
        assertEquals(orderId, response.getData().getOrderId());
        assertEquals(OrderStatus.PREPARING, response.getData().getOrderStatus());
    }

    @Test
    void updateOrder_ShouldUpdateOrderSuccessfully() {
        // Given
        Long orderId = 1L;
        OrderStatus newStatus = OrderStatus.PREPARING;
        String newPaymentMethod = "Credit Card";

        Order mockOrder = Order.builder()
                .orderId(orderId)
                .orderStatus(OrderStatus.REQUIRED)
                .paymentMethod("Cash")
                .totalPrice(BigDecimal.valueOf(10000))
                .build();

        given(orderRepository.findById(orderId)).willReturn(Optional.of(mockOrder));

        // When
        ResponseDto<UpdateOrderResponseDto> response = orderService.updateOrder(orderId, newStatus, newPaymentMethod);

        // Then
        assertTrue(response.getSuccess());
        assertEquals(orderId, response.getData().getOrderId());
        assertEquals(newStatus, response.getData().getOrderStatus());
        assertEquals(newPaymentMethod, response.getData().getPaymentMethod());
        verify(orderRepository).findById(orderId);
    }

}