package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistRequest;
import com.ecommerce.wishlist.adapter.exception.NotFoundException;
import com.ecommerce.wishlist.domain.wishlist.entity.Item;
import com.ecommerce.wishlist.domain.wishlist.entity.WishlistEntity;
import com.ecommerce.wishlist.domain.wishlist.repository.WishlistRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveItemFromWishlistUsecaseTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private RemoveItemFromWishlistUsecase usecase;

    @Captor
    private ArgumentCaptor<WishlistEntity> wishlistCaptor;

    @Test
    @DisplayName("Should remove existing item and persist updated wishlist")
    void removeExistingItem() {
        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setUserId("user1");
        Item toRemove = new Item("tenis1");
        wishlist.getItems().add(toRemove);
        wishlist.getItems().add(new Item("tenis2"));
        when(wishlistRepository.findByUserId("user1")).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        WishlistRequest request = WishlistRequest.builder().userId("user1").item(new Item("tenis1")).build();
        usecase.execute(request);

        verify(wishlistRepository).save(wishlistCaptor.capture());
        WishlistEntity saved = wishlistCaptor.getValue();
        assertThat(saved.getItems()).doesNotContain(toRemove);
        assertThat(saved.getItems()).hasSize(1);
    }

    @Test
    @DisplayName("Should do nothing (no error) when item not in wishlist but save still called with unchanged set")
    void removeAbsentItem() {
        WishlistEntity wishlist = new WishlistEntity();
        wishlist.setUserId("user1");
        wishlist.getItems().add(new Item("tenis2"));
        int originalSize = wishlist.getItems().size();
        when(wishlistRepository.findByUserId("user1")).thenReturn(Optional.of(wishlist));
        when(wishlistRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        WishlistRequest request = WishlistRequest.builder().userId("user1").item(new Item("missing")).build();
        usecase.execute(request);

        verify(wishlistRepository).save(wishlistCaptor.capture());
        assertThat(wishlistCaptor.getValue().getItems()).hasSize(originalSize);
    }

    @Test
    @DisplayName("Should throw exception when wishlist not found")
    void wishlistNotFound() {
        when(wishlistRepository.findByUserId("userX")).thenReturn(Optional.empty());
        WishlistRequest request = WishlistRequest.builder().userId("userX").item(new Item("tenis1")).build();

        assertThatThrownBy(() -> usecase.execute(request))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Wishlist not found");
        verify(wishlistRepository, never()).save(any());
    }
}

