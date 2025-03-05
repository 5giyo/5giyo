package com.example.ogiyo.domain.coupon.controller;


import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.etc.JwtProperties;
import com.example.ogiyo.domain.coupon.dto.request.CreateCouponRequestDto;
import com.example.ogiyo.domain.coupon.entity.Coupon;
import com.example.ogiyo.domain.coupon.service.CouponServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/coupons")
public class CouponController {

    private CouponServiceImpl couponServiceimpl;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> createCoupon(
            @RequestHeader(JwtProperties.HEADER_STRING) String token,
            @Valid @RequestBody CreateCouponRequestDto requestDto
    ) {
        return ResponseEntity.ok(couponServiceimpl.createCoupon(token,requestDto));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<?>> getAllCoupons(
            @PathVariable Long memberId
    ) {
        return ResponseEntity.ok(couponServiceimpl.findAllCoupons(memberId));
    }

    @GetMapping
    public void getCouponById(@RequestParam("id") Long id) {

    }

    @PatchMapping
    public void updateCoupon(@RequestBody Coupon coupon) {

    }

    @DeleteMapping
    public void deleteCoupon(@RequestBody Coupon coupon) {
        couponServiceimpl.deleteCoupon(coupon.getId());
    }


}
