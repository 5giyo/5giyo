package com.example.ogiyo.domain.cart.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.domain.cart.dto.request.AddCartRequestDto;
import com.example.ogiyo.domain.cart.dto.response.GetCartResponseDto;
import com.example.ogiyo.domain.cart.dto.request.UpdateCartRequestDto;
import com.example.ogiyo.domain.cart.dto.response.AddCartResponseDto;

import com.example.ogiyo.domain.cart.dto.response.UpdateCartResponseDto;
import jakarta.validation.Valid;

public interface CartService {

    ResponseDto<AddCartResponseDto> addCart(String token,AddCartRequestDto addCartRequestDto);

    ResponseDto<UpdateCartResponseDto> updateCart
            (String token,
             @Valid UpdateCartRequestDto updateCartRequestDto);

    ResponseDto<GetCartResponseDto> getCart(Long cartId);

    ResponseDto<String> deleteCard(Long cartId);
}
