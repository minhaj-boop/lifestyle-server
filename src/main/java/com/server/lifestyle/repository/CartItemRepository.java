package com.server.lifestyle.repository;

import com.server.lifestyle.model.Cart;
import com.server.lifestyle.model.CartItem;
import com.server.lifestyle.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
