package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistItemResponse;
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
public class GetWishlistItemUsecase implements UseCase<WishlistRequest, WishlistItemResponse> {

    private static final Logger log = LoggerFactory.getLogger(GetWishlistItemUsecase.class);
    private final WishlistRepository wishlistRepository;

    public GetWishlistItemUsecase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public WishlistItemResponse execute(WishlistRequest request) {
        String userId = request.userId();
        Item item = request.item();
        log.info("Checking if item {} exists in user {}'s wishlist", item.itemId(), userId);

        WishlistEntity wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() -> {
                    log.warn("No wishlist found for user {}", userId);
                    return new NotFoundException("User: " + userId + " does not have a wishlist.");
                });

        log.info("Wishlist found for user {}. Checking for item {}", userId, item.itemId());
        Boolean exists = wishlist.getItems().contains(item);
        return WishlistItemResponse.builder().exists(exists).item(item).build();

    }
}
