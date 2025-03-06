package com.example.ogiyo.domain.cart.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.cart.dto.request.AddCartRequestDto;
import com.example.ogiyo.domain.cart.dto.response.AddCartResponseDto;
import com.example.ogiyo.domain.cart.entity.Cart;
import com.example.ogiyo.domain.cart.entity.CartItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;

import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CartServiceImplTest {


    @Mock
    private JwtUtil jwtUtil;
    @Mock
    private RedisTemplate<String, Cart> redisTemplate;
    @Mock
    private ValueOperations<String, Cart> valueOperations;


    @InjectMocks
    private CartServiceImpl cartService;


    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void 새_장바구니에_아이템_추가_성공() {
        // Given
        String token = "테스트토큰";
        Long memberId = 1L;
        AddCartRequestDto requestDto = new AddCartRequestDto(1L, 2);

        when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
        when(valueOperations.get("cart:" + memberId)).thenReturn(null);

        // When
        ResponseDto<AddCartResponseDto> response = cartService.addCart(token, requestDto);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().getItems().size());
        assertEquals(2, response.getData().getTotalQuantity());

        verify(valueOperations).set(eq("cart:" + memberId), any(Cart.class), eq(24L), eq(TimeUnit.HOURS));
    }

    @Test
    void 기존_장바구니에_아이템_추가_성공() {
        // Given
        String token = "테스트토큰";
        Long memberId = 1L;
        AddCartRequestDto requestDto = new AddCartRequestDto(2L, 3);

        Cart existingCart = new Cart(memberId);
        existingCart.addItem(new CartItem(1L, 2));

        when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
        when(valueOperations.get("cart:" + memberId)).thenReturn(existingCart);

        // When
        ResponseDto<AddCartResponseDto> response = cartService.addCart(token, requestDto);

        // Then
        assertNotNull(response);
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertNotNull(response.getData());
        assertEquals(2, response.getData().getItems().size());
        assertEquals(5, response.getData().getTotalQuantity());

        verify(valueOperations).set(eq("cart:" + memberId), any(Cart.class), eq(24L), eq(TimeUnit.HOURS));
    }
}