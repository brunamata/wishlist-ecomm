package com.ecommerce.wishlist.adapter.exception;

public abstract class BasicException extends RuntimeException {
    protected BasicException(String message) {
        super(message);
    }
}
