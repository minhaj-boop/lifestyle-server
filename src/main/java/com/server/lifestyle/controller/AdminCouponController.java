package com.server.lifestyle.controller;

import com.server.lifestyle.model.Cart;
import com.server.lifestyle.model.Coupon;
import com.server.lifestyle.model.User;
import com.server.lifestyle.service.CartService;
import com.server.lifestyle.service.CouponService;
import com.server.lifestyle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupon")
public class AdminCouponController {
    private final CouponService couponService;
    private final UserService userService;
    private final CartService cartService;

    @PostMapping("/admin/apply")
    public ResponseEntity<Cart> applyCoupon(
            @RequestParam String apply,
            @RequestParam String code,
            @RequestParam double orderValue,
            @RequestHeader("Authorization") String jwtToken
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        Cart cart;

        if(apply.equals("true")) {
            cart = couponService.applyCoupon(code, orderValue, user);
        } else {
            cart = couponService.removeCoupon(code, user);
        }

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) throws Exception {
        Coupon newCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.ok(newCoupon);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) throws Exception {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok("Coupon deleted successfully");
    }

    @GetMapping("/admin/get/all")
    public ResponseEntity<List<Coupon>> getAllCoupon() throws Exception {
        List<Coupon> coupons = couponService.findAllCoupon();
        return ResponseEntity.ok(coupons);
    }

}
