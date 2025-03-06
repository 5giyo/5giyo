package com.example.ogiyo.domain.cart.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class GetCartResponseDto {
    private final List<GetCartItemResponseDto> items;
    private final int totalQuantity;


}
