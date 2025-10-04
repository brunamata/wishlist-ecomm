package com.ecommerce.wishlist.domain.userwishlist.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class UserWishlistEntity {

    @Id
    private String userId;

    private String[] wishlistItems;
}
