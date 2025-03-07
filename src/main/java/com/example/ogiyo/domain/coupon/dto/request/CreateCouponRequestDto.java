package com.example.ogiyo.domain.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class CreateCouponRequestDto {
    private String couponCode;
    private BigDecimal discountPrice;
    private BigDecimal maxDiscountPrice;
    private BigDecimal minDeliveryPrice;
    private String status;

    public CreateCouponRequestDto(String couponCode, BigDecimal discountPrice, BigDecimal maxDiscountPrice, BigDecimal minDeliveryPrice, String status) {
        this.couponCode = couponCode;
        this.discountPrice = discountPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.minDeliveryPrice = minDeliveryPrice;
        this.status = status;
    }
}
