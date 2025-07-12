package com.server.lifestyle.controller;

import com.server.lifestyle.model.Product;
import com.server.lifestyle.model.User;
import com.server.lifestyle.model.WishList;
import com.server.lifestyle.service.ProductService;
import com.server.lifestyle.service.UserService;
import com.server.lifestyle.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/wishlist")
public class WishlistController {
    private final WishListService wishListService;
    private final UserService userService;
    private final ProductService productService;

//    @PostMapping("/create")
//    public ResponseEntity<WishList> createWishList(@RequestBody User user) {
//        WishList wishList = wishListService.createWishList(user);
//        return ResponseEntity.ok(wishList);
//    }

    @GetMapping("/get")
    public ResponseEntity<WishList> getWishListByUser(@RequestHeader("Authorization") String jwtToken) throws Exception {
        User user = userService.findUserByJwtToken(jwtToken);
        WishList wishList = wishListService.getWishListByUserId(user);
        return ResponseEntity.ok(wishList);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<WishList> addProductToWishList(@RequestHeader("Authorization") String jwtToken, @PathVariable Long productId) throws Exception {
        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwtToken);
        WishList updatedWishList = wishListService.addProductToWishList(user, product);

        return ResponseEntity.ok(updatedWishList);
    }





}
