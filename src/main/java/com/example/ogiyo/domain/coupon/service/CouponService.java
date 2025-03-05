package com.example.ogiyo.domain.coupon.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.domain.coupon.dto.request.UpdateCouponRequestDto;
import com.example.ogiyo.domain.coupon.dto.request.CreateCouponRequestDto;
import com.example.ogiyo.domain.coupon.dto.response.CreateCouponResponseDto;
import com.example.ogiyo.domain.coupon.dto.response.GetCouponResponseDto;
import com.example.ogiyo.domain.coupon.dto.response.UpdateCouponResponseDto;

import java.util.List;

public interface CouponService {

    ResponseDto<CreateCouponResponseDto> createCoupon(String token, CreateCouponRequestDto createCouponRequestDto);

    ResponseDto<List<GetCouponResponseDto>> findAllCoupons(Long userId);

    ResponseDto<GetCouponResponseDto> findOrderById(Long couponId);

    ResponseDto<UpdateCouponResponseDto> updateCoupon(Long couponid, UpdateCouponRequestDto dto);

    ResponseDto<String> deleteCoupon(Long orderId);
}
