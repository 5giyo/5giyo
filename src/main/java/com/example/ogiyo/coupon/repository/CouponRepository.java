package com.example.ogiyo.coupon.repository;

import com.example.ogiyo.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
