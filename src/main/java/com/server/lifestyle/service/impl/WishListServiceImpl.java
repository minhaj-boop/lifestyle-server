package com.server.lifestyle.service.impl;

import com.server.lifestyle.model.Product;
import com.server.lifestyle.model.User;
import com.server.lifestyle.model.WishList;
import com.server.lifestyle.repository.WishListRepository;
import com.server.lifestyle.service.WishListService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListServiceImpl implements WishListService {

    private final WishListRepository wishListRepository;

    @Override
    public WishList createWishList(User user) {
        WishList wishList = new WishList();
        wishList.setUser(user);
        return wishListRepository.save(wishList);
    }

    @Override
    public WishList getWishListByUserId(User user) {

        WishList wishList =  wishListRepository.findByUser(user);
        if (wishList == null) {
            wishList = createWishList(user);
        }
        return wishList;
    }

//    @Override
//    public WishList deleteWishListById(String Id , User user) {
//        WishList wishList = wishListRepository.findByUser(user);
//        if(wishList != null) {
//           wishList
//        }
//
//    }

    @Override
    public WishList addProductToWishList(User user, Product product) {
        WishList wishList =  getWishListByUserId(user);
        if (wishList.getProducts().contains(product)) {
            wishList.getProducts().remove(product);

        } else {
            wishList.getProducts().add(product);
        }

        return wishListRepository.save(wishList);
    }
}
