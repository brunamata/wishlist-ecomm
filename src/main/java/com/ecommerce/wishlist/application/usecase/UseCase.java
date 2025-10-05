package com.ecommerce.wishlist.application.usecase;

public interface UseCase<I,O> {

    O execute(I input);
}
