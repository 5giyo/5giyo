package com.example.ogiyo.domain.cart.dto.response;

import com.example.ogiyo.domain.cart.entity.CartItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetCartResponseDto {
    private final List<CartItem> items;
    private final int totalQuantity;
}
