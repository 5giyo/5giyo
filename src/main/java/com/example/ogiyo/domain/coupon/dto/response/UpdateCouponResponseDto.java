package com.example.ogiyo.domain.coupon.dto.response;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class UpdateCouponResponseDto {
    private final BigDecimal discountAmount;
    private final BigDecimal maxDiscountPrice;
    private final BigDecimal minDeliveryPrice;
    private final String status;
}
