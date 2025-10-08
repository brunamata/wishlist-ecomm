package com.ecommerce.wishlist.adapter.controller;


import com.ecommerce.wishlist.adapter.dto.WishlistItemResponse;
import com.ecommerce.wishlist.adapter.dto.WishlistResponse;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
@Tag(name = "Wishlist", description = "Endpoints for managing user wishlists")
public interface WishlistResource {

    @Operation(summary = "Get the user wishlist",
            description = "Returns all items in the wishlist for the provided user.")
    @ApiResponse(responseCode = "200", description = "Wishlist retrieved",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    WishlistResponse getWishlistByUserId(
            @Parameter(description = "Unique identifier of the user", example = "user123")
            @PathVariable String userId);

    @Operation(summary = "Check if an item exists in user's wishlist",
            description = "Returns item details plus a flag indicating existence.")
    @ApiResponse(responseCode = "200", description = "Item lookup result",
            content = @Content(schema = @Schema(implementation = WishlistItemResponse.class)))
    @ApiResponse(responseCode = "404", description = "Wishlist not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @GetMapping("/{userId}/items/{itemId}")
    @ResponseStatus(HttpStatus.OK)
    WishlistItemResponse getWishlistItem(
            @Parameter(description = "User identifier", example = "user123") @PathVariable String userId,
            @Parameter(description = "Item identifier", example = "tenis1") @PathVariable String itemId);

    @Operation(summary = "Add an item to the wishlist",
            description = "Adds a new item to the user's wishlist.")
    @ApiResponse(responseCode = "201", description = "Item added",
            content = @Content(schema = @Schema(implementation = WishlistResponse.class)))
    @ApiResponse(responseCode = "400", description = "Wishlist item limit exceeded", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @PostMapping("/{userId}/items")
    @ResponseStatus(HttpStatus.CREATED)
    WishlistResponse addItemToWishlist(
            @Parameter(description = "User identifier", example = "user123") @PathVariable String userId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Item to add",
                    required = true,
                    content = @Content(schema = @Schema(implementation = Item.class))
            )
            @RequestBody Item request);

    @Operation(summary = "Remove an item from an existing wishlist",
            description = "Removes the specified item from the user's wishlist. No error if the item does not exist, but wishlist must exist.")
    @ApiResponse(responseCode = "204", description = "Item removed (or not present)", content = @Content)
    @ApiResponse(responseCode = "404", description = "Wishlist not found", content = @Content)
    @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    @DeleteMapping("/{userId}/items/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void removeItemFromWishlist(
            @Parameter(description = "User identifier", example = "user123") @PathVariable String userId,
            @Parameter(description = "Item identifier", example = "tenis1") @PathVariable String itemId);
}
