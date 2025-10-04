package com.ecommerce.wishlist.adapter.controller;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wishlist")
public interface UserWishlistResource {


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String getWishlist();
}
