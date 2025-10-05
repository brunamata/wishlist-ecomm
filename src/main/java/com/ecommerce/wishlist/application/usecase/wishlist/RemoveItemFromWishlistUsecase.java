package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.exception.NotFoundException;
import com.ecommerce.wishlist.adapter.dto.WishlistRequest;
import com.ecommerce.wishlist.application.usecase.UseCase;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import com.ecommerce.wishlist.domain.wishlist.repository.WishlistRepository;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import org.springframework.stereotype.Component;

@Component
public class RemoveItemFromWishlistUsecase implements UseCase<WishlistRequest, Void> {

    private final WishlistRepository wishlistRepository;

    public RemoveItemFromWishlistUsecase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public Void execute(WishlistRequest request) {
        String userId = request.userId();
        Item item = request.item();

        WishlistEntity wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(
                        () -> new NotFoundException("Wishlist not found for userId: " + userId)
                );

        wishlist.getItems().remove(item);
        wishlistRepository.save(wishlist);
        return null;
    }

}

