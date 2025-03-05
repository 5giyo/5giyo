package com.example.ogiyo.domain.cart.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AddCartResponseDto {
    private final String menuName;
    private final int price;
    private final int totalPrice;
    private final int quantity;

}
