package com.server.lifestyle.service;

import com.server.lifestyle.model.Cart;
import com.server.lifestyle.model.CartItem;
import com.server.lifestyle.model.Product;
import com.server.lifestyle.model.User;

public interface CartService {
     CartItem addCartItem(User user, Product product, String size, int quantity);
     Cart findUserCart(User user);

}
