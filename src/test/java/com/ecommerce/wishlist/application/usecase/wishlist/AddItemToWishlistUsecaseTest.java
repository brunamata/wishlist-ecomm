package com.ecommerce.wishlist.application.usecase.wishlist;

import com.ecommerce.wishlist.adapter.dto.WishlistRequest;
import com.ecommerce.wishlist.adapter.dto.WishlistResponse;
import com.ecommerce.wishlist.adapter.exception.ExceededLimitSizeException;
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

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddItemToWishlistUsecaseTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private AddItemToWishlistUsecase usecase;

    @Captor
    private ArgumentCaptor<WishlistEntity> wishlistCaptor;

    private final String userId = "user123";
    
    @Test
    @DisplayName("Should add item to existing wishlist and persist")
    void addItemExistingWishlist() {
        WishlistEntity existing = new WishlistEntity();
        existing.setUserId(userId);
        existing.getItems().add(new Item("tenis1"));

        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.of(existing));
        when(wishlistRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Item newItem = new Item("tenis2");
        WishlistRequest request = WishlistRequest.builder().userId(userId).item(newItem).build();

        WishlistResponse response = usecase.execute(request);

        verify(wishlistRepository).save(wishlistCaptor.capture());
        WishlistEntity saved = wishlistCaptor.getValue();

        assertThat(saved.getItems()).contains(newItem);
        assertThat(response.items()).contains(newItem);
        assertThat(response.userId()).isEqualTo(userId);
    }

    @Test
    @DisplayName("Should create new wishlist if none exists and add item")
    void createWishlistIfAbsent() {
        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.empty());
        when(wishlistRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Item item = new Item("tenis1");
        WishlistRequest request = WishlistRequest.builder().userId(userId).item(item).build();

        WishlistResponse response = usecase.execute(request);

        verify(wishlistRepository).save(wishlistCaptor.capture());
        WishlistEntity saved = wishlistCaptor.getValue();

        assertThat(saved.getUserId()).isEqualTo(userId);
        assertThat(saved.getItems()).containsExactly(item);
        assertThat(response.items()).containsExactly(item);
    }

    @Test
    @DisplayName("Should throw when adding 21st item (limit exceeded)")
    void exceedLimit() {
        WishlistEntity existing = new WishlistEntity();
        existing.setUserId(userId);

        Set<Item> items = new HashSet<>();
        for(int i=1;i<=20;i++) {
            items.add(new Item("tenis"+i));
        }
        existing.getItems().addAll(items);

        when(wishlistRepository.findByUserId(userId)).thenReturn(Optional.of(existing));

        Item extra = new Item("tenis21");
        WishlistRequest request = WishlistRequest.builder().userId(userId).item(extra).build();

        assertThatThrownBy(() -> usecase.execute(request))
                .isInstanceOf(ExceededLimitSizeException.class)
                .hasMessageContaining("more than 20");

        verify(wishlistRepository, never()).save(any());
    }
}

