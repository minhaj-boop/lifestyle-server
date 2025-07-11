package com.server.lifestyle.controller;

import com.server.lifestyle.exceptions.ProductException;
import com.server.lifestyle.model.Cart;
import com.server.lifestyle.model.CartItem;
import com.server.lifestyle.model.Product;
import com.server.lifestyle.model.User;
import com.server.lifestyle.request.AddItemRequest;
import com.server.lifestyle.response.ApiResponse;
import com.server.lifestyle.service.CartItemService;
import com.server.lifestyle.service.CartService;
import com.server.lifestyle.service.ProductService;
import com.server.lifestyle.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping("/find")
    public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/item/add")
    public ResponseEntity<CartItem> addItemToCart (@RequestHeader("Authorization") String jwt, @RequestBody AddItemRequest req) throws ProductException, Exception {
        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(req.getProductId());

        CartItem cartItem = cartService.addCartItem(user, product, req.getSize(), req.getQuantity());

        ApiResponse res = new ApiResponse();
        res.setMessage("Successfully added item to the cart");

        return new ResponseEntity<>(cartItem, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/item/delete/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteItemFromCart(@PathVariable Long cartItemId, @RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);
        ApiResponse res = new ApiResponse();
        res.setMessage("Successfully deleted item from the cart");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/update/{cartItemId}")
    public ResponseEntity<CartItem> updateItemFromCart(@PathVariable Long cartItemId, @RequestBody CartItem cartItem, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        CartItem updateCartItem = null;
        if(cartItem.getQuantity()>0) {
            updateCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        }
        return new ResponseEntity<>(updateCartItem, HttpStatus.ACCEPTED);
    }
}
