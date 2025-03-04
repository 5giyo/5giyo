package com.example.ogiyo.coupon.service;

import com.example.ogiyo.coupon.dto.request.UpdateCouponRequestDto;
import com.example.ogiyo.coupon.dto.response.CouponResponseDto;
import com.example.ogiyo.coupon.dto.request.CreateCouponRequestDto;
import com.example.ogiyo.coupon.entity.Coupon;
import com.example.ogiyo.order.dto.response.OrderResponseDto;

import java.awt.*;
import java.util.List;

public interface CouponService {

    CouponResponseDto createCoupon(Long userId, CreateCouponRequestDto createCouponRequestDto);

    List<CouponResponseDto> findAllCoupons(Long userId);

    CouponResponseDto findOrderById(Long orderId);

    void updateCoupon(Long userid, UpdateCouponRequestDto dto);

    void deleteCoupon(Long orderId);
}
