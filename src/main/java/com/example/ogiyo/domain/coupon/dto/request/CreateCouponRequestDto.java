package com.example.ogiyo.domain.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateCouponRequestDto {

    private String couponCode;
    private CouponType couponType;
    private int discountRate;
    private int discountAmount;
    private int maxDiscountPrice;
    private int minDeliveryPrice;
    private String status;
}
