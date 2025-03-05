package com.example.ogiyo.domain.coupon.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class GetCouponResponseDto {
    private final long couponId;
    private final String couponCode;
    private final int minDiscountRate;
    private final int maxDiscountPrice;
}
