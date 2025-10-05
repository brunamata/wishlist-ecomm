package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistResponse;
import com.ecommerce.wishlist.application.usecase.UseCase;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import com.ecommerce.wishlist.domain.wishlist.repository.WishlistRepository;
import org.springframework.stereotype.Component;

@Component
public class GetUserWishlistUsecase implements UseCase<String, WishlistResponse> {

    private final WishlistRepository wishlistRepository;

    public GetUserWishlistUsecase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public WishlistResponse execute(String userId) {
        return wishlistRepository.findByUserId(userId)
                .map( WishlistResponse::fromEntity )
                .orElseGet(() -> {
                            WishlistEntity newWishlist = new WishlistEntity();
                            newWishlist.setUserId(userId);
                            return WishlistResponse.fromEntity(wishlistRepository.save(newWishlist));
                        }
                );
    }
}
