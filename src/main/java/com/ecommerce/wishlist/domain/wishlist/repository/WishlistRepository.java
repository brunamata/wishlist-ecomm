package com.ecommerce.wishlist.domain.wishlist.repository;

import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface WishlistRepository extends MongoRepository<WishlistEntity, String>{

    @Query("{userId:'?0'}")
    Optional<WishlistEntity> findByUserId(String userId);
}
