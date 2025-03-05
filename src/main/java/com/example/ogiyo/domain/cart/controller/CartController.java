package com.example.ogiyo.domain.cart.controller;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.etc.JwtProperties;
import com.example.ogiyo.domain.cart.dto.request.AddCartRequestDto;
import com.example.ogiyo.domain.cart.dto.request.UpdateCartRequestDto;
import com.example.ogiyo.domain.cart.service.CartServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/carts")
public class CartController {

    private final CartServiceImpl cartServiceImpl;

    //TODO:장바구니 추가
    @PostMapping
    public ResponseEntity<ResponseDto<?>> addCart(
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @Valid @RequestBody AddCartRequestDto addCartRequestDto
    ) {
        return ResponseEntity.ok(cartServiceImpl.addCart(token,addCartRequestDto));
    }

    //TODO:장바구니 수정
    @PatchMapping("/{cartId}")
    public ResponseEntity<ResponseDto<?>> updateCart(
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @Valid @RequestBody UpdateCartRequestDto updateCartRequestDto
    ) {
        return ResponseEntity.ok(cartServiceImpl.updateCart(token,updateCartRequestDto));
    }

    //TODO:장바구니 조회

    //TODO:장바구니 삭제
}
