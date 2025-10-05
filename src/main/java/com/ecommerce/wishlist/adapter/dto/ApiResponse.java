package com.ecommerce.wishlist.adapter.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

@Builder
@JsonDeserialize
public record ApiResponse(
    String type,
    String message
) {
}
