package com.ecommerce.wishlist.domain.wishlist.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Document(collection = "wishlists")
public class WishlistEntity {

    @Id
    private String id;

    @Indexed(unique = true)
    private String userId;

    private Set<Item> items = new HashSet<>();
}
