package com.ecommerce.wishlist.domain.userwishlist;

import com.ecommerce.wishlist.domain.userwishlist.entity.UserWishlistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserWishlistRepository extends MongoRepository<UserWishlistEntity, String>{}
