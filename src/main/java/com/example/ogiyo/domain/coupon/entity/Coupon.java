package com.example.ogiyo.domain.coupon.entity;

import com.example.ogiyo.domain.coupon.dto.request.UpdateCouponRequestDto;
import com.example.ogiyo.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "coupons")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String couponCode;

    private int discountRate;
    private int discountAmount;
    private int maxDiscountPrice;
    private int minDeliveryPrice;
    private String status;

    @Enumerated(EnumType.STRING)
    private String couponType;



    @ManyToOne
    private Member member;


    public Coupon() {

    }

    public void update(UpdateCouponRequestDto dto) {
        this.discountAmount = discountAmount;
        this.maxDiscountPrice = maxDiscountPrice;
        this.minDeliveryPrice = minDeliveryPrice;
        this.couponType = couponType;

    }
}
