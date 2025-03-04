package com.example.ogiyo.coupon.dto.request;

import lombok.Getter;

@Getter
public class CreateCouponRequestDto {

    private String couponCode;
    private String couponType;
    private int discountPrice;
    private int discountAmount;
    private int maxDiscountPrice;
    private int minDiscountPrice;
    private String status;
}
