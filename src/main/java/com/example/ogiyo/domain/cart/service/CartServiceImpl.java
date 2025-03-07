package com.example.ogiyo.domain.cart.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.cart.dto.request.AddCartRequestDto;
import com.example.ogiyo.domain.cart.dto.request.UpdateCartRequestDto;
import com.example.ogiyo.domain.cart.dto.response.AddCartResponseDto;
import com.example.ogiyo.domain.cart.dto.response.GetCartResponseDto;
import com.example.ogiyo.domain.cart.dto.response.UpdateCartResponseDto;
import com.example.ogiyo.domain.cart.entity.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor //의존성 주입!
public class CartServiceImpl implements CartService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Cart> redisTemplate;

    //장바구니 추가
    @Override
    @Transactional
    public ResponseDto<AddCartResponseDto> addCart(String token, AddCartRequestDto addCartRequestDto) {
        Long memberId = jwtUtil.extractMemberId(token);
        String redisKey = "cart:" + memberId;

        Cart cart = redisTemplate.opsForValue().get(redisKey);
        if (cart == null) {
            cart = new Cart(memberId);
        }

        cart.addItem(addCartRequestDto.getMenuId(), addCartRequestDto.getQuantity());

        redisTemplate.opsForValue().set(redisKey, cart, 24, TimeUnit.HOURS);

        AddCartResponseDto responseDto = new AddCartResponseDto(
                cart.getItems(),
                cart.getTotalQuantity()
        );

        return ResponseDto.success(responseDto);
    }

    @Override
    @Transactional
    public ResponseDto<UpdateCartResponseDto> updateCart(String token, Long cartId, UpdateCartRequestDto updateCartRequestDto) {
        Long memberId = jwtUtil.extractMemberId(token);
        String redisKey = "cart:" + memberId;

        Cart cart = redisTemplate.opsForValue().get(redisKey);
        if (cart == null) {
            throw new IllegalArgumentException("장바구니가 존재하지 않습니다.");
        }

        cart.updateItemQuantity(updateCartRequestDto.getMenuId(), updateCartRequestDto.getQuantity());

        redisTemplate.opsForValue().set(redisKey, cart, 24, TimeUnit.HOURS);

        UpdateCartResponseDto responseDto = new UpdateCartResponseDto(
                updateCartRequestDto.getMenuId(),
                updateCartRequestDto.getQuantity()
        );

        return ResponseDto.success(responseDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ResponseDto<GetCartResponseDto> getCart(Long cartId) {
        String redisKey = "cart:" + cartId;

        Cart cart = redisTemplate.opsForValue().get(redisKey);
        if (cart == null) {
            throw new IllegalArgumentException("장바구니가 조회되지 않습니다.");
        }

        // GetCartResponseDto 생성 (Map<Long, Integer>와 총 수량 전달)
        GetCartResponseDto responseDto = new GetCartResponseDto(
                cart.getItems(), // Map<Long, Integer> 그대로 전달
                cart.getTotalQuantity() // 총 수량 계산
        );

        return ResponseDto.success(responseDto);
    }

    @Override
    @Transactional
    public ResponseDto<String> deleteCard(Long cartId) {
        String redisKey = "cart:" + cartId;
        redisTemplate.opsForValue().getAndDelete(redisKey);
        return ResponseDto.success("장바구니를 삭제하였습니다.");
    }
}