package com.ecommerce.wishlist.adapter.controller;

import com.ecommerce.wishlist.adapter.dto.ApiResponse;
import com.ecommerce.wishlist.adapter.dto.ResponseType;
import com.ecommerce.wishlist.adapter.exception.ExceededLimitSizeException;
import com.ecommerce.wishlist.adapter.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WishlistControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse> customNotFoundExceptionHandler(NotFoundException ex) {
        ApiResponse body = ApiResponse
                .builder()
                .type(ResponseType.ERROR.name())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    @ExceptionHandler(ExceededLimitSizeException.class)
    public ResponseEntity<ApiResponse> customExceededLimitSizeExceptionHandler(ExceededLimitSizeException ex) {
        ApiResponse body = ApiResponse
                .builder()
                .type(ResponseType.ERROR.name())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }


}
