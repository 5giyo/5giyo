package com.example.ogiyo.domain.coupon.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.util.JwtUtil;
import com.example.ogiyo.domain.coupon.dto.request.CreateCouponRequestDto;
import com.example.ogiyo.domain.coupon.dto.request.GetCouponRequestDto;
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

    //쿠폰생성하기
    @Override
    public ResponseDto<CreateCouponResponseDto> createCoupon(
            CreateCouponRequestDto createCouponRequestDto) {

        Coupon newCoupon = Coupon.builder()
                .couponCode(createCouponRequestDto.getCouponCode())
                .discountPrice(createCouponRequestDto.getDiscountPrice())
                .maxDiscountPrice(createCouponRequestDto.getMaxDiscountPrice())
                .minDeliveryPrice(createCouponRequestDto.getMinDeliveryPrice())
                .build();

        Coupon savedCoupon = couponRepository.save(newCoupon);

        // 4. 응답 DTO 생성
        CreateCouponResponseDto responseDto = CreateCouponResponseDto.builder()
                .couponId(savedCoupon.getId())
                .couponCode(savedCoupon.getCouponCode())
                .discountPrice(savedCoupon.getDiscountPrice())
                .maxDiscountPrice(savedCoupon.getMaxDiscountPrice())
                .minDeliveryPrice(savedCoupon.getMinDeliveryPrice())
                .build();

        return ResponseDto.success(responseDto);
    }

    //쿠폰 전체 조회하기
    @Override
    public ResponseDto<List<Coupon>> findAllCoupons() {
        List<Coupon> coupons = couponRepository.findAll();
        return ResponseDto.success(coupons);
    }

    //쿠폰 조회
    @Override
    public ResponseDto<GetCouponResponseDto> getCoupon(Long couponId) {
        Coupon coupon = couponRepository
                .findById(couponId)
                .orElseThrow(()->new IllegalArgumentException("쿠폰을 조회할 수 없습니다."));
        GetCouponResponseDto getCoupon = new GetCouponResponseDto(
                coupon.getId(),
                coupon.getCouponCode(),
                coupon.getMaxDiscountPrice()
        );
        return ResponseDto.success(getCoupon);
    }

    //쿠폰수정하기(최소배달비,최대할인금액,할인금액,쿠폰상태)
    @Override
    public ResponseDto<UpdateCouponResponseDto> updateCoupon(Long couponId,
                                                             UpdateCouponRequestDto dto) {

        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(()->new IllegalArgumentException("해당 쿠폰을 조회할 수 없습니다."));

        coupon.update(dto);

        UpdateCouponResponseDto updateCoupon = new UpdateCouponResponseDto(
                dto.getMinDeliveryPrice(),
                dto.getMaxDiscountPrice(),
                dto.getDiscountPrice(),
                dto.getStatus()
        );

        return ResponseDto.success(updateCoupon);
    }

    //쿠폰삭제
    @Override
    public ResponseDto<String> deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);
        return ResponseDto.success("쿠폰이 삭제되었습니다.");
    }

    //쿠폰코드 찾는 메서드
    public Coupon findByCouponCode(String couponCode) {
        return couponRepository.findByCouponCode(couponCode)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 쿠폰 코드입니다."));
    }


}
