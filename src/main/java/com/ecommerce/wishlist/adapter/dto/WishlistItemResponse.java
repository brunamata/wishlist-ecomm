package com.ecommerce.wishlist.adapter.dto;

import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

@Builder
@JsonDeserialize
public record WishlistItemResponse(
        Boolean exists,
        Item item
) { }
