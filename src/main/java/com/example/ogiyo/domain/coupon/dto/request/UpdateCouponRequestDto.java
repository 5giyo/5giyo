package com.example.ogiyo.domain.coupon.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class UpdateCouponRequestDto {
    private BigDecimal discountPrice;
    private BigDecimal maxDiscountPrice;
    private BigDecimal minDeliveryPrice;
    private String status;

    //디 맥 민
    public UpdateCouponRequestDto(BigDecimal discountPrice, BigDecimal maxDiscountPrice, BigDecimal minDeliveryPrice, String status) {
        this.discountPrice = discountPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.minDeliveryPrice = minDeliveryPrice;
        this.status = status;
    }
}
