package com.example.ogiyo.domain.coupon.controller;


import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.domain.coupon.dto.request.CreateCouponRequestDto;
import com.example.ogiyo.domain.coupon.dto.request.GetCouponRequestDto;
import com.example.ogiyo.domain.coupon.dto.request.UpdateCouponRequestDto;
import com.example.ogiyo.domain.coupon.entity.Coupon;
import com.example.ogiyo.domain.coupon.service.CouponServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coupons")
public class CouponController {

    private CouponServiceImpl couponServiceimpl;

    @PostMapping
    public ResponseEntity<ResponseDto<?>> createCoupon(
            @Valid @RequestBody CreateCouponRequestDto requestDto
    ) {
        return ResponseEntity.ok(couponServiceimpl.createCoupon(requestDto));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<?>> getAllCoupons(
    ) {
        return ResponseEntity.ok(couponServiceimpl.findAllCoupons());
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<ResponseDto<?>> getCouponById(@PathVariable Long couponId,
                                                        @RequestBody GetCouponRequestDto requestDto) {
        return ResponseEntity.ok(couponServiceimpl.getCoupon(couponId));
    }

    @PatchMapping("/{couponId}")
    public ResponseEntity<ResponseDto<?>> updateCoupon(@PathVariable Long couponId,
                                                       @RequestBody UpdateCouponRequestDto requestDto) {
        return ResponseEntity.ok(couponServiceimpl.updateCoupon(couponId,requestDto));
    }

    @DeleteMapping
    public void deleteCoupon(@RequestBody Coupon coupon) {
        couponServiceimpl.deleteCoupon(coupon.getCouponId());
    }


}
