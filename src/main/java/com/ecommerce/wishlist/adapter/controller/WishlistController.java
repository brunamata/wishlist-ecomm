package com.ecommerce.wishlist.adapter.controller;


import com.ecommerce.wishlist.adapter.dto.WishlistItemResponse;
import com.ecommerce.wishlist.adapter.dto.WishlistRequest;
import com.ecommerce.wishlist.adapter.dto.WishlistResponse;
import com.ecommerce.wishlist.application.usecase.wishlist.GetWishlistItemUsecase;
import com.ecommerce.wishlist.application.usecase.wishlist.GetUserWishlistUsecase;
import com.ecommerce.wishlist.application.usecase.wishlist.RemoveItemFromWishlistUsecase;
import com.ecommerce.wishlist.application.usecase.wishlist.AddItemToWishlistUsecase;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import org.springframework.stereotype.Component;

@Component
public class WishlistController implements WishlistResource {

    private final GetWishlistItemUsecase getWishlistItemUsecase;
    private final GetUserWishlistUsecase getUserWishlistUsecase;
    private final AddItemToWishlistUsecase addItemToWishlistUsecase;
    private final RemoveItemFromWishlistUsecase removeItemFromWishlistUsecase;

    public WishlistController(
            GetWishlistItemUsecase getWishlistItemUsecase,
            GetUserWishlistUsecase getUserWishlistUsecase,
            AddItemToWishlistUsecase addItemToWishlistUsecase,
            RemoveItemFromWishlistUsecase removeItemFromWishlistUsecase
    ) {
        this.getWishlistItemUsecase = getWishlistItemUsecase;
        this.getUserWishlistUsecase = getUserWishlistUsecase;
        this.addItemToWishlistUsecase = addItemToWishlistUsecase;
        this.removeItemFromWishlistUsecase = removeItemFromWishlistUsecase;
    }

    @Override
    public WishlistResponse getWishlistByUserId(String userId) {
        return getUserWishlistUsecase.execute(userId);
    }

    @Override
    public WishlistItemResponse getWishlistItem(String userId, String itemId) {
        return getWishlistItemUsecase.execute(new WishlistRequest(userId, new Item(itemId)));
    }

    @Override
    public WishlistResponse addItemToWishlist(String userId, Item request) {
        return addItemToWishlistUsecase.execute(new WishlistRequest(userId, request));
    }

    @Override
    public void removeItemFromWishlist(String userId, String itemId) {
        removeItemFromWishlistUsecase.execute(new WishlistRequest(userId, new Item(itemId)));
    }
}
