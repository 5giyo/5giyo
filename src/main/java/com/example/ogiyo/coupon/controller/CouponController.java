package com.example.ogiyo.coupon.controller;


import com.example.ogiyo.coupon.entity.Coupon;
import com.example.ogiyo.coupon.service.CouponServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("/api/v1/coupons")
public class CouponController {

    private CouponServiceImpl couponServiceimpl;

    @PostMapping
    public Coupon createCoupon(@RequestBody Coupon coupon) {

    }

    @GetMapping
    public List<Coupon> getAllCoupons() {
        return
    }

    @GetMapping
    public ResponseEntity<Coupon> getCouponById(@RequestParam("id") long id) {

    }

    @PatchMapping
    public Coupon updateCoupon(@RequestBody Coupon coupon) {

    }

    @DeleteMapping
    public void deleteCoupon(@RequestBody Coupon coupon) {

    }


}
