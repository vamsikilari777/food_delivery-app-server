package com.brundhavan.foodapi.services;

import com.brundhavan.foodapi.IO.CartRequest;
import com.brundhavan.foodapi.IO.CartResponse;

public interface CartService {

    CartResponse addToCart(CartRequest request);

    CartResponse getCart();

    void clearCart();

    CartResponse removeFromCart(CartRequest cartRequest);
}