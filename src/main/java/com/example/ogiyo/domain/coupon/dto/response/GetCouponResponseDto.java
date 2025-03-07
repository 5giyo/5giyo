package com.example.ogiyo.domain.coupon.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class GetCouponResponseDto {
    private final long couponId;
    private final String couponCode;
    private final BigDecimal maxDiscountPrice;
}
