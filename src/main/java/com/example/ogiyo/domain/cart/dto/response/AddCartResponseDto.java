package com.example.ogiyo.domain.cart.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class AddCartResponseDto {
    private final Map<Long, Integer> items;
    private final int totalQuantity;


}
