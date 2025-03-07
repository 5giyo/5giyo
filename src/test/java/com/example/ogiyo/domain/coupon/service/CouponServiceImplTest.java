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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class CouponServiceImplTest {
    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponServiceImpl couponService;

    private Coupon testCoupon;
    private CreateCouponRequestDto createCouponRequestDto;

    @BeforeEach
    void setUp() {
        testCoupon = Coupon.builder()
                .couponId(1L)  // ID 명시적 설정
                .couponCode("TEST123")
                .discountPrice(new BigDecimal("1000"))
                .maxDiscountPrice(new BigDecimal("5000"))
                .minDeliveryPrice(new BigDecimal("10000"))
                .status("ACTIVATE")
                .build();

        createCouponRequestDto = new CreateCouponRequestDto(
                "TEST123",
                new BigDecimal("1000"),
                new BigDecimal("5000"),
                new BigDecimal("10000"),
                "ACTIVATE"
        );
    }

    @Test
    void createCoupon() {
        when(couponRepository.save(any(Coupon.class))).thenReturn(testCoupon);

        ResponseDto<CreateCouponResponseDto> response = couponService.createCoupon(createCouponRequestDto);

        assertNotNull(response);
        assertEquals(testCoupon.getCouponId(), response.getData().getCouponId());
        assertEquals(testCoupon.getCouponCode(), response.getData().getCouponCode());
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }

    @Test
    void findAllCoupons() {
        List<Coupon> coupons = Arrays.asList(testCoupon);
        when(couponRepository.findAll()).thenReturn(coupons);

        ResponseDto<List<Coupon>> response = couponService.findAllCoupons();

        assertNotNull(response);
        assertEquals(1, response.getData().size());
        assertEquals(testCoupon, response.getData().get(0));
        verify(couponRepository, times(1)).findAll();
    }

    @Test
    void getCoupon() {
        when(couponRepository.findById(1L)).thenReturn(Optional.of(testCoupon));

        ResponseDto<GetCouponResponseDto> response = couponService.getCoupon(1L);

        assertNotNull(response);
        assertEquals(testCoupon.getCouponId(), response.getData().getCouponId());
        assertEquals(testCoupon.getCouponCode(), response.getData().getCouponCode());
        verify(couponRepository, times(1)).findById(1L);
    }

    @Test
    void updateCoupon() {
        UpdateCouponRequestDto updateDto = new UpdateCouponRequestDto(
                new BigDecimal("1500"),  // discountPrice
                new BigDecimal("6000"),  // maxDiscountPrice
                new BigDecimal("15000"), // minDeliveryPrice
                "ACTIVATE"// 디 맥 민
        );
        when(couponRepository.findById(1L)).thenReturn(Optional.of(testCoupon));

        ResponseDto<UpdateCouponResponseDto> response = couponService.updateCoupon(1L, updateDto);

        assertNotNull(response);
        UpdateCouponResponseDto updatedCoupon = response.getData();

        // 허용 오차
        BigDecimal tolerance = new BigDecimal("0.001");

        // discountPrice 비교
        assertTrue(updateDto.getDiscountPrice().subtract(updatedCoupon.getDiscountPrice()).abs().compareTo(tolerance) < 0,
                "Discount price should be within tolerance");


        // maxDiscountPrice 비교
        assertTrue(updateDto.getMaxDiscountPrice().subtract(updatedCoupon.getMaxDiscountPrice()).abs().compareTo(tolerance) < 0,
                "Max discount price should be within tolerance");

        // minDeliveryPrice 비교
        assertTrue(updateDto.getMinDeliveryPrice().subtract(updatedCoupon.getMinDeliveryPrice()).abs().compareTo(tolerance) < 0,
                "Min delivery price should be within tolerance");

        // status 비교 (문자열이므로 정확히 일치해야 함)
        assertEquals(updateDto.getStatus(), updatedCoupon.getStatus(), "Status should match exactly");

        verify(couponRepository, times(1)).findById(1L);
    }

    @Test
    void findByCouponCode() {
        when(couponRepository.findByCouponCode("TEST123")).thenReturn(Optional.of(testCoupon));

        Coupon result = couponService.findByCouponCode("TEST123");

        assertNotNull(result);
        assertEquals(testCoupon, result);
        verify(couponRepository, times(1)).findByCouponCode("TEST123");
    }

    @Test
    void findByCouponCode_NotFound() {
        when(couponRepository.findByCouponCode("INVALID")).thenReturn(Optional.empty());

        assertThrows(NotFoundCouponException.class, () -> couponService.findByCouponCode("INVALID"));
        verify(couponRepository, times(1)).findByCouponCode("INVALID");
    }
}