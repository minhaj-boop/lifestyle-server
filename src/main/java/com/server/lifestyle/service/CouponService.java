package com.server.lifestyle.service;

import com.server.lifestyle.model.Cart;
import com.server.lifestyle.model.Coupon;
import com.server.lifestyle.model.User;
import com.server.lifestyle.request.CreateCouponRequest;

import java.util.List;

public interface CouponService {
    Cart applyCoupon(String code, double orderValue, User user) throws Exception;
    Cart removeCoupon(String code, User user) throws Exception;
    Coupon findCouponById(Long id) throws Exception;
    Coupon createCoupon(CreateCouponRequest req);
    List<Coupon> findAllCoupon();
    void deleteCoupon(Long id) throws Exception;

}
