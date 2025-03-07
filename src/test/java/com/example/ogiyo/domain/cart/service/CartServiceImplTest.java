package com.example.ogiyo.domain.cart.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.cart.dto.request.AddCartRequestDto;
import com.example.ogiyo.domain.cart.dto.request.UpdateCartRequestDto;
import com.example.ogiyo.domain.cart.dto.response.AddCartResponseDto;
import com.example.ogiyo.domain.cart.dto.response.GetCartResponseDto;
import com.example.ogiyo.domain.cart.dto.response.UpdateCartResponseDto;
import com.example.ogiyo.domain.cart.entity.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.HashMap;
import java.util.Map;
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

    private final String token = "testToken";
    private final Long memberId = 1L;


    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }


    @Test
    void addCart_성공() {
        // Given
        String redisKey = "cart:" + memberId;
        AddCartRequestDto requestDto = new AddCartRequestDto(1L, 2);
        Cart cart = new Cart(memberId);

        when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
        when(valueOperations.get(redisKey)).thenReturn(cart);

        // When
        ResponseDto<AddCartResponseDto> response = cartService.addCart(token, requestDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getData());
        assertEquals(1, response.getData().getItems().size());
        assertEquals(2, response.getData().getTotalQuantity());

        verify(valueOperations).set(eq(redisKey), any(Cart.class), eq(24L), eq(TimeUnit.HOURS));
    }

    @Test
    void updateCart_성공() {
        // Given
        String redisKey = "cart:" + memberId;
        UpdateCartRequestDto requestDto = new UpdateCartRequestDto(1L, 5);
        Cart cart = new Cart(memberId);
        cart.addItem(1L, 2);

        when(jwtUtil.extractMemberId(token)).thenReturn(memberId);
        when(valueOperations.get(redisKey)).thenReturn(cart);

        // When
        ResponseDto<UpdateCartResponseDto> response = cartService.updateCart(token, memberId, requestDto);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getData());
        assertEquals(1L, response.getData().getMenuId());
        assertEquals(5, response.getData().getQuantity());

        verify(valueOperations).set(eq(redisKey), any(Cart.class), eq(24L), eq(TimeUnit.HOURS));
    }

    @Test
    void getCart_성공() {
        // Given
        String redisKey = "cart:" + memberId;
        Cart cart = new Cart(memberId);
        cart.addItem(1L, 2);

        when(valueOperations.get(redisKey)).thenReturn(cart);

        // When
        ResponseDto<GetCartResponseDto> response = cartService.getCart(memberId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertNotNull(response.getData());
        assertEquals(cart.getItems(), response.getData().getItems());
        assertEquals(2, response.getData().getTotalQuantity());
    }

    @Test
    void deleteCard_성공() {
        // Given
        String redisKey = "cart:" + memberId;
        when(valueOperations.getAndDelete(redisKey)).thenReturn(new Cart(memberId));

        // When
        ResponseDto<String> response = cartService.deleteCard(memberId);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatus());
        assertEquals("장바구니를 삭제하였습니다.", response.getData());

        verify(valueOperations).getAndDelete(redisKey);
    }
}