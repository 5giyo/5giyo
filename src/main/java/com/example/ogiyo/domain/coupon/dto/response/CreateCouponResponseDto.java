package com.example.ogiyo.domain.coupon.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
@Builder
public class CreateCouponResponseDto {
    private final long couponId;
    private final String couponCode;
    private final String status;
    private final BigDecimal maxDiscountPrice;
    private final BigDecimal discountPrice;
    private final BigDecimal minDeliveryPrice;
}
