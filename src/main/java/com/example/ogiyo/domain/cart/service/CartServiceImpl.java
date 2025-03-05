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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor //의존성 주입!
public class CartServiceImpl implements CartService {

    private final JwtUtil jwtUtil;
    private final RedisTemplate<String, Cart> redisTemplate;


    @Override
    public ResponseDto<AddCartResponseDto> addCart(
            String token,
            AddCartRequestDto addCartRequestDto) {
        Long memberId = jwtUtil.extractUserId(token);
        String rediskey = "cart:" + memberId;

        Cart cart;
        CartItem cartItem;

        if (redisTemplate.hasKey(rediskey)) {
            cart = redisTemplate.opsForValue().get(rediskey);
        } else {
            cart = new Cart();
            cart.setMemberId(memberId);
            cart.setItems(new ArrayList<>());
        }

        redisTemplate.opsForValue().set(rediskey, cart,24, TimeUnit.HOURS);

        AddCartResponseDto addCart = new AddCartResponseDto(

        )

        return ResponseDto.success(
                );
    }

    @Override
    public ResponseDto<UpdateCartResponseDto> updateCart(String token, UpdateCartRequestDto updateCartRequestDto) {
        return null;
    }


    @Override
    public ResponseDto<GetCartResponseDto> getCart(long cartId) {
        return null;
    }


}
