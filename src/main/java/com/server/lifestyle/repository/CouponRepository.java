package com.server.lifestyle.repository;

import com.server.lifestyle.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon findByCode(String couponCode);
}
