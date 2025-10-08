package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistItemResponse;
import com.ecommerce.wishlist.adapter.dto.WishlistRequest;
import com.ecommerce.wishlist.adapter.exception.NotFoundException;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import com.ecommerce.wishlist.domain.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetWishlistItemUsecaseTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private GetWishlistItemUsecase usecase;

    @Test
    @DisplayName("Should return exists=true when item present")
    void itemPresent() {
        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setUserId("user1");
        Item target = new Item("tenis1");
        wishlist.getItems().add(target);
        when(wishlistRepository.findByUserId("user1")).thenReturn(Optional.of(wishlist));

        WishlistRequest request = WishlistRequest.builder().userId("user1").item(new Item("tenis1")).build();
        WishlistItemResponse resp = usecase.execute(request);

        assertThat(resp.exists()).isTrue();
        assertThat(resp.item()).isEqualTo(target);
    }

    @Test
    @DisplayName("Should return exists=false when item absent")
    void itemAbsent() {
        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setUserId("user1");
        wishlist.getItems().add(new Item("tenis1"));
        when(wishlistRepository.findByUserId("user1")).thenReturn(Optional.of(wishlist));

        WishlistRequest request = WishlistRequest.builder().userId("user1").item(new Item("tenis999" )).build();
        WishlistItemResponse resp = usecase.execute(request);

        assertThat(resp.exists()).isFalse();
        assertThat(resp.item()).isEqualTo(new Item("tenis999"));
    }

    @Test
    @DisplayName("Should throw NotFoundException when wishlist not found")
    void wishlistNotFound() {
        when(wishlistRepository.findByUserId("user1")).thenReturn(Optional.empty());
        WishlistRequest request = WishlistRequest.builder().userId("user1").item(new Item("x")).build();

        assertThatThrownBy(() -> usecase.execute(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("does not have a wishlist");
    }
}

