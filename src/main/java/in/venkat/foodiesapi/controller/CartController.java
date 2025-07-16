package in.venkat.foodiesapi.controller;


import in.venkat.foodiesapi.exception.BadRequestException;
import in.venkat.foodiesapi.io.CartRequest;
import in.venkat.foodiesapi.io.CartResponse;
import in.venkat.foodiesapi.service.CartService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.Map;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {


    private  final CartService cartService;

    @PostMapping
    public CartResponse addToCart(@RequestBody CartRequest request){
        String foodId =request.getFoodId();
        if (foodId == null || foodId.isBlank()) {
            throw new BadRequestException("foodId must not be empty");
        }

        return cartService.addToCart(request);
    }

    @GetMapping
    public CartResponse getCart(){
         return cartService.getCart();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(){
        cartService.clearCart();
    }

    @PostMapping("/remove")
    public  CartResponse removeFromCart(@RequestBody CartRequest request){
        String foodId = request.getFoodId();
        if (foodId == null || foodId.isBlank()) {
            throw new BadRequestException("foodId must not be empty");
        }
        return cartService.removeFromCart(request);
    }



}
