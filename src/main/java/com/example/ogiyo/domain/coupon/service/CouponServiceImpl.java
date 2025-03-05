package com.example.ogiyo.domain.coupon.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.coupon.dto.request.CreateCouponRequestDto;
import com.example.ogiyo.domain.coupon.dto.request.UpdateCouponRequestDto;
import com.example.ogiyo.domain.coupon.dto.response.CreateCouponResponseDto;
import com.example.ogiyo.domain.coupon.dto.response.GetCouponResponseDto;
import com.example.ogiyo.domain.coupon.dto.response.UpdateCouponResponseDto;
import com.example.ogiyo.domain.coupon.entity.Coupon;
import com.example.ogiyo.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final JwtUtil jwtUtil;


    //쿠폰생성하기
    @Override
    public ResponseDto<CreateCouponResponseDto> createCoupon(String token,
                                                             CreateCouponRequestDto createCouponRequestDto) {
        Long memberId = jwtUtil.extractUserId(token);




        return ResponseDto.success();
    }

    //쿠폰 전체 조회하기
    @Override
    public ResponseDto<List<GetCouponResponseDto>> findAllCoupons(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(()->new IllegalArgumentException("쿠폰을 찾지 못했습니다."));
        GetCouponResponseDto getCoupon = new GetCouponResponseDto(


        )


        return ResponseDto.success();
    }

    //쿠폰 단건조회
    //    private final long couponId;
    //    private final String couponCode;
    //    private final int minDiscountRate;
    //    private final int maxDiscountPrice;
    @Override
    public ResponseDto<GetCouponResponseDto> findOrderById(Long couponId) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(()-> new IllegalArgumentException("쿠폰을 찾지 못했습니다."));

        if (coupon.getCouponType().equals("정률")) {
            GetCouponResponseDto getCoupon = new GetCouponResponseDto(
                    coupon.getId(),
                    coupon.getCouponCode(),
                    coupon.getDiscountRate(),
                    coupon.getMaxDiscountPrice(),
                    coupon.getMinDeliveryPrice()
            );
        }else if (coupon.getCouponType().equals("정액")){
            GetCouponResponseDto getCoupon = new GetCouponResponseDto(
                    coupon.getId(),
                    coupon.getCouponCode(),
                    coupon.getDiscountRate(),
                    coupon.getMaxDiscountPrice(),
                    coupon.getMinDeliveryPrice()
            );
        }




        return ResponseDto.success(getCoupon);
    }

    //쿠폰수정하기(금액,유효기간,등등)
    @Override
    public ResponseDto<UpdateCouponResponseDto> updateCoupon(Long couponId,
                                                             UpdateCouponRequestDto dto) {



        return ResponseDto.success();
    }

    @Override
    public ResponseDto<String> deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);
        return ResponseDto.success("쿠폰이 삭제되었습니다.");
    }
}
