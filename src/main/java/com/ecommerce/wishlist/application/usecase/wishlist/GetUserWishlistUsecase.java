package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistResponse;
import com.ecommerce.wishlist.application.usecase.UseCase;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import com.ecommerce.wishlist.domain.wishlist.repository.WishlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GetUserWishlistUsecase implements UseCase<String, WishlistResponse> {

    private static final Logger log = LoggerFactory.getLogger(GetUserWishlistUsecase.class);
    private final WishlistRepository wishlistRepository;

    public GetUserWishlistUsecase(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    @Override
    public WishlistResponse execute(String userId) {
        log.info("Retrieving wishlist for user {}", userId);
        return wishlistRepository.findByUserId(userId)
                .map( WishlistResponse::fromEntity )
                .orElseGet(() -> {
                            log.info("No wishlist found for user {}. Creating a new one.", userId);
                            WishlistEntity newWishlist = new WishlistEntity();
                            newWishlist.setUserId(userId);
                            return WishlistResponse.fromEntity(wishlistRepository.save(newWishlist));
                        }
                );
    }
}
