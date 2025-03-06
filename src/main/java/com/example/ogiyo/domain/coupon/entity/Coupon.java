package com.example.ogiyo.domain.coupon.entity;

import com.example.ogiyo.common.entity.BaseEntity;
import com.example.ogiyo.domain.coupon.dto.request.UpdateCouponRequestDto;
import com.example.ogiyo.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Entity
public class Coupon extends BaseEntity {

    //정액할인 쿠폰만 구현하기
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String couponCode;
    private BigDecimal discountPrice;
    private BigDecimal maxDiscountPrice;
    private BigDecimal minDeliveryPrice;
    private boolean isUsed;

    @ManyToOne
    private Member member;

    @Builder
    public Coupon(String couponCode, BigDecimal discountPrice, BigDecimal maxDiscountPrice, BigDecimal minDeliveryPrice, boolean isUsed) {
        this.couponCode = couponCode;
        this.discountPrice = discountPrice;
        this.maxDiscountPrice = maxDiscountPrice;
        this.minDeliveryPrice = minDeliveryPrice;
        this.isUsed = isUsed;
    }

    public Coupon() {

    }

    public void update(UpdateCouponRequestDto dto) {
        this.discountPrice = dto.getDiscountPrice();
        this.maxDiscountPrice = dto.getMaxDiscountPrice();
        this.minDeliveryPrice = dto.getMinDeliveryPrice();
    }
}
