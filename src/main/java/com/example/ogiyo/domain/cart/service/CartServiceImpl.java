package com.example.ogiyo.domain.cart.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.cart.dto.request.AddCartRequestDto;
import com.example.ogiyo.domain.cart.dto.request.UpdateCartRequestDto;
import com.example.ogiyo.domain.cart.dto.response.AddCartResponseDto;
import com.example.ogiyo.domain.cart.dto.response.GetCartResponseDto;
import com.example.ogiyo.domain.cart.dto.response.UpdateCartResponseDto;
import com.example.ogiyo.domain.cart.entity.Cart;
import com.example.ogiyo.domain.cart.entity.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor //의존성 주입!
public class CartServiceImpl implements CartService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Cart> redisTemplate;


    @Override
    public ResponseDto<AddCartResponseDto> addCart(String Token,
                                                   AddCartRequestDto addCartRequestDto) {
        Long memberId = jwtUtil.extractUserId(Token);
        String redisKey = "cart:" + memberId;

        //레디스에서 장바구니 정보 조회. 없으면 새로 생성
        Cart cart = redisTemplate.opsForValue().get(redisKey);
        if (cart == null) {
            cart = new Cart(memberId);
        }


        //새 CartItem 생성 및 추가
        CartItem newItem = new CartItem(
                addCartRequestDto.getMenuId(),
                addCartRequestDto.getQuantity()
        );
        cart.addItem(newItem);

        //수정된 장바구니를 Redis에 저장
        redisTemplate.opsForValue().set(redisKey, cart,24, TimeUnit.HOURS);

        //응답 DTO 생성
        AddCartResponseDto responseDto = new AddCartResponseDto(
                new ArrayList<>(cart.getItems().values()),
                cart.getTotalQuantity()
        );
        return ResponseDto.success(responseDto);
    }

    @Override
    public ResponseDto<UpdateCartResponseDto> updateCart(String token,
                                                         UpdateCartRequestDto updateCartRequestDto) {
        Long memberId = jwtUtil.extractUserId(token);
        String redisKey = "cart:" + memberId;

        // 레디스에서 장바구니 정보 조회
        Cart cart = redisTemplate.opsForValue().get(redisKey);
        if (cart == null) {
            throw new IllegalArgumentException("장바구니가 조회되지 않습니다.");
        }
        // 해당 아이템의 수량 업데이트
        cart.updateItemQuantity(updateCartRequestDto.getMenuId(), updateCartRequestDto.getQuantity());
        redisTemplate.opsForValue().set(redisKey, cart,24, TimeUnit.HOURS);

        // 응답 DTO 생성
        UpdateCartResponseDto responseDto = new UpdateCartResponseDto(
                updateCartRequestDto.getMenuId(),
                updateCartRequestDto.getQuantity()
        );

        return ResponseDto.success(responseDto);
    }

    @Override
    public ResponseDto<GetCartResponseDto> getCart(Long cartId) {
        String redisKey = "cart:" + cartId;

        // 레디스에서 장바구니 정보 조회
        Cart cart = redisTemplate.opsForValue().get(redisKey);
        if (cart == null) {
            throw new IllegalArgumentException("장바구니가 조회되지 않습니다.");
        }

        GetCartResponseDto responseDto = new GetCartResponseDto(
                new ArrayList<>(cart.getItems().values()),
                cart.getTotalQuantity()
        );
        return ResponseDto.success(responseDto);
    }

    @Override
    public ResponseDto<String> deleteCard(Long cartId) {
        String rediskey = "cart:" + cartId;
        redisTemplate.delete(rediskey);
        return ResponseDto.success("장바구니를 삭제하였습니다.");
    }


}
