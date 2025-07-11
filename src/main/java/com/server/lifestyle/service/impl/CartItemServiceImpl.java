package com.server.lifestyle.service.impl;

import com.server.lifestyle.model.CartItem;
import com.server.lifestyle.model.User;
import com.server.lifestyle.repository.CartItemRepository;
import com.server.lifestyle.service.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItems) throws Exception {
        CartItem item = findCartItemById(id);

        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)) {
            item.setQuantity(cartItems.getQuantity());
            item.setMrPrice(item.getQuantity()*item.getProduct().getMrPrice());
            item.setSize(cartItems.getSize());
            item.setSellingPrice(item.getQuantity()*item.getProduct().getSellingPrice());
            return cartItemRepository.save(item);
        }
        throw new Exception("You can't update another user cart item!");

    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {
        CartItem item = findCartItemById(cartItemId);

        User cartItemUser = item.getCart().getUser();

        if(cartItemUser.getId().equals(userId)) {
            cartItemRepository.delete(item);
        } else {
            throw new Exception("You can't delete this item!");
        }
    }

    @Override
    public CartItem findCartItemById(Long id) throws Exception {
        return cartItemRepository.findById(id).orElseThrow(()->
            new Exception("Cart item not found!")
        );
    }
}
