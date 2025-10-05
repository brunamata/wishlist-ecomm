package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistRequest;
import com.ecommerce.wishlist.adapter.dto.WishlistResponse;
import com.ecommerce.wishlist.adapter.exception.ExceededLimitSizeException;
import com.ecommerce.wishlist.application.usecase.UseCase;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import com.ecommerce.wishlist.domain.wishlist.repository.WishlistRepository;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import org.springframework.stereotype.Component;

@Component
public class AddItemToWishlistUsecase implements UseCase<WishlistRequest, WishlistResponse> {

    private final WishlistRepository wishlistRepository;
    private static final int MAX_ITEMS = 20;

    public AddItemToWishlistUsecase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public WishlistResponse execute(WishlistRequest request) {
        String userId = request.userId();
        Item item = request.item();

        WishlistEntity wishlist = wishlistRepository.findByUserId(userId)
                .orElseGet(() -> {
                    WishlistEntity newWishlist = new WishlistEntity();
                    newWishlist.setUserId(userId);
                    return newWishlist;
                });

        wishlist.getItems().add(item);

        if(wishlist.getItems().size() > MAX_ITEMS) {
            throw new ExceededLimitSizeException("Wishlist cannot contain more than " + MAX_ITEMS + " items.");
        }

        return WishlistResponse.fromEntity(wishlistRepository.save(wishlist));
    }
}
