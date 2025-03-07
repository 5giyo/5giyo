package com.example.ogiyo.domain.cart.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class GetCartResponseDto {
    private final Map<Long, Integer> items; // 메뉴 ID와 수량을 직접 저장
    private final int totalQuantity;
}
