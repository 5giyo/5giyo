package com.example.ogiyo.domain.coupon.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateCouponResponseDto {
    private final long couponId;
    private final String couponCode;
    private final String Status;
    private final int MaxDiscountPrice;
    private final int DiscountAmount;
    private final int DiscountRate;
    private CouponType couponType;

}
