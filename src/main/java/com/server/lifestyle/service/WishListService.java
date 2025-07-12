package com.server.lifestyle.service;

import com.server.lifestyle.model.Product;
import com.server.lifestyle.model.User;
import com.server.lifestyle.model.WishList;

public interface WishListService {
    WishList createWishList(User user);
    WishList getWishListByUserId(User user);
    WishList addProductToWishList(User user, Product product);

}
