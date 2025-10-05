package com.ecommerce.wishlist.adapter.dto;

import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import lombok.Builder;

@Builder
public record WishlistRequest(
        String userId,
        Item item
) { }
