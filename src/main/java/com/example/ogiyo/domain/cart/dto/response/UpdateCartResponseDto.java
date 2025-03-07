package com.example.ogiyo.domain.cart.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class UpdateCartResponseDto {
    private final Long menuId;
    private final int quantity;
}
