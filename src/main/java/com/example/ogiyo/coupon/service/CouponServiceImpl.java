//package com.example.ogiyo.coupon.service;
//
//import com.example.ogiyo.coupon.dto.request.CreateCouponRequestDto;
//import com.example.ogiyo.coupon.dto.request.UpdateCouponRequestDto;
//import com.example.ogiyo.coupon.dto.response.CouponResponseDto;
//import com.example.ogiyo.coupon.repository.CouponRepository;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@AllArgsConstructor
//public class CouponServiceImpl implements CouponService {
//
//    private final CouponRepository couponRepository;
//
//
//    @Override
//    public CouponResponseDto createCoupon(Long userId, CreateCouponRequestDto createCouponRequestDto) {
//        return null;
//    }
//
//    @Override
//    public List<CouponResponseDto> findAllCoupons(Long userId) {
//        return List.of();
//    }
//
//    @Override
//    public CouponResponseDto findOrderById(Long orderId) {
//        return null;
//    }
//
//    @Override
//    public void updateCoupon(Long userid, UpdateCouponRequestDto dto) {
//
//    }
//
//    @Override
//    public void deleteCoupon(Long couponId) {
//        couponRepository.deleteById(couponId);
//    }
//}
