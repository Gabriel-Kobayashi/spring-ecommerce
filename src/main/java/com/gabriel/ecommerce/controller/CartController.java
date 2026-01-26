package com.gabriel.ecommerce.controller;

import com.gabriel.ecommerce.dto.cart.AddtoCartRequestDto;
import com.gabriel.ecommerce.entity.Cart;
import com.gabriel.ecommerce.entity.User;
import com.gabriel.ecommerce.service.CartService;
import com.gabriel.ecommerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    public CartController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Cart> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getAuthenticatedUser(userDetails);
        Cart cart = cartService.getCartByUser(user);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<Cart> addProduct(@AuthenticationPrincipal UserDetails userDetails, @RequestBody AddtoCartRequestDto requestDto) {
        User user = userService.getAuthenticatedUser(userDetails);

        Cart cart = cartService.addProduct(
                user,
                requestDto.productId(),
                requestDto.quantity()
        );
        return ResponseEntity.ok(cart);
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<Cart> updateQuantity(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId, @RequestParam int quantity) {
        User user = userService.getAuthenticatedUser(userDetails);

        Cart cart = cartService.updateQuantity(user, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeProduct(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId) {
        User user = userService.getAuthenticatedUser(userDetails);

        cartService.removeProduct(user, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getAuthenticatedUser(userDetails);

        cartService.clearCart(user);
        return ResponseEntity.noContent().build();
    }
}
