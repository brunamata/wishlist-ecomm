package com.ecommerce.wishlist.adapter.controller;


import com.ecommerce.wishlist.adapter.dto.WishlistItemResponse;
import com.ecommerce.wishlist.adapter.dto.WishlistResponse;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wishlist")
public interface WishlistResource {

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    WishlistResponse getWishlistByUserId(@PathVariable String userId);

    @GetMapping("/{userId}/items/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    WishlistItemResponse getWishlistItem(@PathVariable String userId, @PathVariable String itemId);

    @PostMapping("/{userId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    WishlistResponse addItemToWishlist(@PathVariable String userId, @RequestBody Item request);

    @DeleteMapping("/{userId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeItemFromWishlist(@PathVariable String userId, @PathVariable String itemId);
}
