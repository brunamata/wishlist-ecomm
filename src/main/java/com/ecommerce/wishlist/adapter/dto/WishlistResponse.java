package com.ecommerce.wishlist.adapter.dto;

import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonDeserialize
public record WishlistResponse(
        String userId,
        Set<Item> items
) {

    public static WishlistResponse fromEntity(WishlistEntity entity) {
        return WishlistResponse.builder()
                .userId(entity.getUserId())
                .items(entity.getItems())
                .build();
    }

}
