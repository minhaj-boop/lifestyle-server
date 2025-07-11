package com.server.lifestyle.service.impl;

import com.server.lifestyle.model.Cart;
import com.server.lifestyle.model.CartItem;
import com.server.lifestyle.model.Product;
import com.server.lifestyle.model.User;
import com.server.lifestyle.repository.CartItemRepository;
import com.server.lifestyle.repository.CartRepository;
import com.server.lifestyle.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem addCartItem(User user, Product product, String size, int quantity) {
        Cart cart = findUserCart(user);

        CartItem isPresent =cartItemRepository.findByCartAndProductAndSize(cart, product, size);

        if(isPresent == null){
            CartItem cartItems = new CartItem();
            cartItems.setProduct(product);
            cartItems.setSize(size);
            cartItems.setQuantity(quantity);
            cartItems.setUserId(user.getId());
            cartItems.setSize(size);

            double totalPrice = quantity * product.getSellingPrice();
            cartItems.setSellingPrice(totalPrice);
            cartItems.setMrPrice(quantity * product.getMrPrice());

            cart.getCartItems().add(cartItems);
            cartItems.setCart(cart);

            return cartItemRepository.save(cartItems);
        }

        return null;
    }

    @Override
    public Cart findUserCart(User user) {
        Cart cart = cartRepository.findByUserId(user.getId());
        double totalPrice = 0;
        double totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItem cartItems : cart.getCartItems()) {
            totalPrice += cartItems.getMrPrice();
            totalDiscountedPrice += cartItems.getSellingPrice();
            totalItem += cartItems.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setDiscount(calculateDiscountPercentage(totalPrice, totalDiscountedPrice));
        cart.setTotalItem(totalItem);

        return cart;
    }

    private int calculateDiscountPercentage(double mrPrice, double sellingPrice) {
        if (mrPrice <=0) {
//            throw new IllegalArgumentException("Actual price be greater than 0.");
            return 0;
        }
        double discount =  mrPrice - sellingPrice;
        double discountPercentage = (discount / mrPrice) * 100;

        return (int) discountPercentage;
    }
}
