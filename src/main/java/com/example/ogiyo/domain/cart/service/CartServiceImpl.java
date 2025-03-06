package com.example.ogiyo.domain.cart.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.cart.dto.request.AddCartRequestDto;
import com.example.ogiyo.domain.cart.dto.request.UpdateCartRequestDto;
import com.example.ogiyo.domain.cart.dto.response.AddCartResponseDto;
import com.example.ogiyo.domain.cart.dto.response.GetCartItemResponseDto;
import com.example.ogiyo.domain.cart.dto.response.GetCartResponseDto;
import com.example.ogiyo.domain.cart.dto.response.UpdateCartResponseDto;
import com.example.ogiyo.domain.cart.entity.Cart;
import com.example.ogiyo.domain.cart.entity.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor //의존성 주입!
public class CartServiceImpl implements CartService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Cart> redisTemplate;

    //장바구니 추가
    @Override
    @Transactional
    public ResponseDto<AddCartResponseDto> addCart(String token,
                                                   AddCartRequestDto addCartRequestDto) {
        Long memberId = jwtUtil.extractMemberId(token);
        String redisKey = "cart:" + memberId;

        // 장바구니에 아이템 추가
        redisTemplate.opsForHash().put(redisKey,
                addCartRequestDto.getMenuId().toString(),
                addCartRequestDto.getQuantity());

        // 만료 시간 설정
        redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);

        // 전체 장바구니 조회
        Map<Object, Object> cartItems = redisTemplate.opsForHash().entries(redisKey);

        // 응답 DTO 생성
        List<CartItem> items = cartItems.entrySet().stream()
                .map(entry -> new CartItem(Long.parseLong(entry.getKey().toString()), (Integer) entry.getValue()))
                .collect(Collectors.toList());

        int totalQuantity = items.stream().mapToInt(CartItem::getQuantity).sum();

        AddCartResponseDto responseDto = new AddCartResponseDto(items, totalQuantity);
        return ResponseDto.success(responseDto);
    }


    //장바구니 수정
    @Override
    @Transactional
    public ResponseDto<UpdateCartResponseDto> updateCart(String token, Long cartId, UpdateCartRequestDto updateCartRequestDto) {
        Long memberId = jwtUtil.extractMemberId(token);
        String redisKey = "cart:" + memberId;

        // 아이템 수량 업데이트
        redisTemplate.opsForHash().put(redisKey,
                updateCartRequestDto.getMenuId().toString(),
                updateCartRequestDto.getQuantity());

        // 응답 DTO 생성
        UpdateCartResponseDto responseDto = new UpdateCartResponseDto(
                updateCartRequestDto.getMenuId(),
                updateCartRequestDto.getQuantity()
        );

        return ResponseDto.success(responseDto);
    }

    @Override
    @Transactional
    public ResponseDto<GetCartResponseDto> getCart(Long cartId) {
        String redisKey = "cart:" + cartId;

        // 장바구니 정보 조회
        Map<Object, Object> cartItems = redisTemplate.opsForHash().entries(redisKey);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("장바구니가 조회되지 않습니다.");
        }

        List<GetCartItemResponseDto> items = cartItems.entrySet().stream()
                .map(entry -> new GetCartItemResponseDto(Long.parseLong(entry.getKey().toString())))
                .collect(Collectors.toList());

        int totalQuantity = cartItems.values().stream()
                .mapToInt(value -> (Integer) value)
                .sum();

        GetCartResponseDto responseDto = new GetCartResponseDto(items, totalQuantity);
        return ResponseDto.success(responseDto);
    }

    @Override
    public ResponseDto<String> deleteCard(Long cartId) {
        String redisKey = "cart:" + cartId;
        redisTemplate.delete(redisKey);
        return ResponseDto.success("장바구니를 삭제하였습니다.");
    }
}