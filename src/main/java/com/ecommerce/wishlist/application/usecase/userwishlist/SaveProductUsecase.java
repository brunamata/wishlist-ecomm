package com.ecommerce.wishlist.application.usecase.userwishlist;

import com.ecommerce.wishlist.domain.userwishlist.UserWishlistRepository;
import com.ecommerce.wishlist.domain.userwishlist.entity.UserWishlistEntity;
import org.springframework.stereotype.Component;

@Component
public class SaveProductUsecase {

    private UserWishlistRepository userWishlistRepository;

    public SaveProductUsecase(UserWishlistRepository userWishlistRepository) {
        this.userWishlistRepository = userWishlistRepository;
    }

    public UserWishlistEntity saveProduct(String userId, String productId) {
        var userWishlist = new UserWishlistEntity();

        userWishlist.setUserId(userId);
        userWishlist.setWishlistItems(new String[]{productId});

        return userWishlistRepository.save(userWishlist);
    }
}
