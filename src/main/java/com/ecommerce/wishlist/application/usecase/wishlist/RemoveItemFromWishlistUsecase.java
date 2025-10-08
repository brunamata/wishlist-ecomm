package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.exception.NotFoundException;
import com.ecommerce.wishlist.adapter.dto.WishlistRequest;
import com.ecommerce.wishlist.application.usecase.UseCase;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import com.ecommerce.wishlist.domain.wishlist.repository.WishlistRepository;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RemoveItemFromWishlistUsecase implements UseCase<WishlistRequest, Void> {

    private static final Logger log = LoggerFactory.getLogger(RemoveItemFromWishlistUsecase.class);

    private final WishlistRepository wishlistRepository;

    public RemoveItemFromWishlistUsecase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public Void execute(WishlistRequest request) {
        String userId = request.userId();
        Item item = request.item();
        log.info("Removing item {} from user {}'s wishlist", item.itemId(), userId);

        WishlistEntity wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("No wishlist found for user {}", userId);
                    return new NotFoundException("Wishlist not found for userId: " + userId);
                });

        log.info("Wishlist found for user {}. Removing item {} if exists", userId, item.itemId());
        wishlist.getItems().remove(item);
        wishlistRepository.save(wishlist);

        log.info("Item {} removed from user {}'s wishlist", item.itemId(), userId);
        return null;
    }

}

