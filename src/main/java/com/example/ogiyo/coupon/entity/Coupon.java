package com.example.ogiyo.coupon.entity;

import com.example.ogiyo.coupon.dto.request.UpdateCouponRequestDto;
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
    private String couponType;
    private int discountPrice;
    private int discountAmount;
    private int maxDiscountPrice;
    private int minDiscountPrice;
    private String status;

    @ManyToOne
    private Member member;


    public Coupon() {

    }

    public void update(UpdateCouponRequestDto dto) {
        this.discountAmount = discountAmount;
        this.maxDiscountPrice = maxDiscountPrice;
        this.minDiscountPrice = minDiscountPrice;
        this.couponType = couponType;

    }
}
