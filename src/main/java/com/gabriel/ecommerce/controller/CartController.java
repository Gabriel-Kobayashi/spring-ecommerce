package com.gabriel.ecommerce.controller;

import com.gabriel.ecommerce.dto.cart.AddToCartRequestDto;
import com.gabriel.ecommerce.dto.cart.CartResponseDto;
import com.gabriel.ecommerce.dto.cart.UpdateCartItemQuantityDto;
import com.gabriel.ecommerce.entity.Cart;
import com.gabriel.ecommerce.entity.User;
import com.gabriel.ecommerce.mapper.CartMapper;
import com.gabriel.ecommerce.service.CartService;
import com.gabriel.ecommerce.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@PreAuthorize("hasRole('USER')")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final CartMapper cartMapper;

    public CartController(CartService cartService, UserService userService, CartMapper cartMapper) {
        this.cartService = cartService;
        this.userService = userService;
        this.cartMapper = cartMapper;
    }

    @GetMapping
    public ResponseEntity<CartResponseDto> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.getAuthenticatedUser(userDetails);
        Cart cart = cartService.getCartByUser(user);
        return ResponseEntity.ok(cartMapper.toCartResponseDto(cart));
    }

    @PostMapping("/items")
    public ResponseEntity<CartResponseDto> addProduct(@AuthenticationPrincipal UserDetails userDetails, @Valid @RequestBody AddToCartRequestDto requestDto) {
        User user = userService.getAuthenticatedUser(userDetails);

        Cart cart = cartService.addProduct(
                user,
                requestDto.productId(),
                requestDto.quantity()
        );
        return ResponseEntity.ok(cartMapper.toCartResponseDto(cart));
    }

    @PutMapping("/items/{productId}")
    public ResponseEntity<CartResponseDto> updateQuantity(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long productId, @Valid @RequestBody UpdateCartItemQuantityDto quantityDto) {
        User user = userService.getAuthenticatedUser(userDetails);

        Cart cart = cartService.updateQuantity(user, productId, quantityDto.quantity());
        return ResponseEntity.ok(cartMapper.toCartResponseDto(cart));
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
