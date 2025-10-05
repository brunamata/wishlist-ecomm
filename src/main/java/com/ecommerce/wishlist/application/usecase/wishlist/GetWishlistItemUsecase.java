package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistItemResponse;
import com.ecommerce.wishlist.adapter.exception.NotFoundException;
import com.ecommerce.wishlist.adapter.dto.WishlistRequest;
import com.ecommerce.wishlist.application.usecase.UseCase;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import com.ecommerce.wishlist.domain.wishlist.repository.WishlistRepository;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import org.springframework.stereotype.Component;

@Component
public class GetWishlistItemUsecase implements UseCase<WishlistRequest, WishlistItemResponse> {

    private final WishlistRepository wishlistRepository;

    public GetWishlistItemUsecase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public WishlistItemResponse execute(WishlistRequest request) {
        String userId = request.userId();
        Item item = request.item();

        WishlistEntity wishlist = wishlistRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new NotFoundException("User: " + userId + " does not have a wishlist.")
                );

        Boolean exists = wishlist.getItems().contains(item);
        return WishlistItemResponse.builder().exists(exists).item(item).build();

    }
}
