package com.example.ogiyo.domain.coupon.service;

import com.example.ogiyo.common.dto.ResponseDto;
import com.example.ogiyo.common.exception.NotFoundCouponException;
import com.example.ogiyo.domain.coupon.dto.request.CreateCouponRequestDto;
import com.example.ogiyo.domain.coupon.dto.request.UpdateCouponRequestDto;
import com.example.ogiyo.domain.coupon.dto.response.CreateCouponResponseDto;
import com.example.ogiyo.domain.coupon.dto.response.GetCouponResponseDto;
import com.example.ogiyo.domain.coupon.dto.response.UpdateCouponResponseDto;
import com.example.ogiyo.domain.coupon.entity.Coupon;
import com.example.ogiyo.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    //쿠폰생성하기
    @Transactional
    @Override
    public ResponseDto<CreateCouponResponseDto> createCoupon(
            CreateCouponRequestDto createCouponRequestDto) {
        System.out.println("엔티티 생성 전");

        Coupon newCoupon = new Coupon(
                createCouponRequestDto.getCouponCode(),
                createCouponRequestDto.getDiscountPrice(),
                createCouponRequestDto.getMaxDiscountPrice(),
                createCouponRequestDto.getMinDeliveryPrice(),
                createCouponRequestDto.getStatus()
        );
        System.out.println("엔티티 생성 후");

        System.out.println("세이브 전");
        Coupon savedCoupon = couponRepository.save(newCoupon);
        System.out.println("세이브 후");

        // 4. 응답 DTO 생성
        CreateCouponResponseDto responseDto = CreateCouponResponseDto.builder()
                .couponId(savedCoupon.getCouponId())
                .couponCode(savedCoupon.getCouponCode())
                .discountPrice(savedCoupon.getDiscountPrice())
                .maxDiscountPrice(savedCoupon.getMaxDiscountPrice())
                .minDeliveryPrice(savedCoupon.getMinDeliveryPrice())
                .build();
        System.out.println("디티오 생성 후");

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
                .orElseThrow(NotFoundCouponException::new);
        GetCouponResponseDto getCoupon = new GetCouponResponseDto(
                coupon.getCouponId(),
                coupon.getCouponCode(),
                coupon.getMaxDiscountPrice()
        );
        return ResponseDto.success(getCoupon);
    }

    //쿠폰수정하기(최소배달비,최대할인금액,할인금액,쿠폰상태)
    @Override
    @Transactional
    public ResponseDto<UpdateCouponResponseDto> updateCoupon(Long couponId, UpdateCouponRequestDto dto) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(NotFoundCouponException::new);

        coupon.update(dto);

        UpdateCouponResponseDto updateCoupon = new UpdateCouponResponseDto(
                coupon.getDiscountPrice(),
                coupon.getMaxDiscountPrice(),
                coupon.getMinDeliveryPrice(),
                coupon.getStatus()
        );

        return ResponseDto.success(updateCoupon);
    }

    //쿠폰삭제
    @Override
    @Transactional
    public ResponseDto<String> deleteCoupon(Long couponId) {
        couponRepository.deleteById(couponId);
        return ResponseDto.success("쿠폰이 삭제되었습니다.");
    }

    //쿠폰코드 찾는 메서드
    public Coupon findByCouponCode(String couponCode) {
        return couponRepository.findByCouponCode(couponCode)
                .orElseThrow(NotFoundCouponException::new);
    }


}
