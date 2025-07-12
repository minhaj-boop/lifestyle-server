package com.server.lifestyle.repository;

import com.server.lifestyle.model.User;
import com.server.lifestyle.model.WishList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<WishList, Long> {
    WishList findByUser(User userId);
}
