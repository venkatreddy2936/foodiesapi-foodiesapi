package in.venkat.foodiesapi.service;


import in.venkat.foodiesapi.io.CartRequest;
import in.venkat.foodiesapi.io.CartResponse;
import org.springframework.stereotype.Service;


public interface CartService {
    CartResponse addToCart(CartRequest request);

    CartResponse getCart();

    void  clearCart();

    CartResponse removeFromCart(CartRequest cartRequest);

}
