package com.ecommerce.wishlist.adapter.controller;


import com.ecommerce.wishlist.application.usecase.userwishlist.SaveProductUsecase;
import com.ecommerce.wishlist.domain.userwishlist.entity.UserWishlistEntity;
import org.springframework.stereotype.Component;

@Component
public class UserWishlistController implements UserWishlistResource {

    private SaveProductUsecase saveProductUsecase;

    public UserWishlistController(SaveProductUsecase saveProductUsecase) {
        this.saveProductUsecase = saveProductUsecase;
    }

    @Override
    public String getWishlist() {

        UserWishlistEntity result = saveProductUsecase.saveProduct("user123", "product456");

        return result.toString();
    }
}
