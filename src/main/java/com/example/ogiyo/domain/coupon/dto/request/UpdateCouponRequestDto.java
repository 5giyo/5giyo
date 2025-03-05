package com.example.ogiyo.domain.coupon.dto.request;

import com.example.ogiyo.domain.coupon.entity.CouponType;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateCouponRequestDto {

    private String couponCode;
    private CouponType couponType;
    private int discountRate;
    private int discountAmount;
    private int maxDiscountPrice;
    private int minDeliveryPrice;
    private String status;
}
