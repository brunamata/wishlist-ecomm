package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistResponse;
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
class GetUserWishlistUsecaseTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private GetUserWishlistUsecase usecase;

    @Test
    @DisplayName("Should return existing wishlist without saving new entity")
    void returnsExisting() {
        WishlistEntity existing = new WishlistEntity();
        existing.setUserId("user1");
        existing.getItems().add(new Item("tenis1"));
        when(wishlistRepository.findByUserId("user1")).thenReturn(Optional.of(existing));

        WishlistResponse response = usecase.execute("user1");

        assertThat(response.userId()).isEqualTo("user1");
        assertThat(response.items()).containsExactlyInAnyOrder(new Item("tenis1"));
        verify(wishlistRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should create new wishlist when none exists and persist it")
    void createsWhenAbsent() {
        when(wishlistRepository.findByUserId("user2")).thenReturn(Optional.empty());
        when(wishlistRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        WishlistResponse response = usecase.execute("user2");

        assertThat(response.userId()).isEqualTo("user2");
        assertThat(response.items()).isEmpty();
    }
}
