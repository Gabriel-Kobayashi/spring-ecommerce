package com.gabriel.ecommerce.mapper;

import com.gabriel.ecommerce.dto.cart.CartItemResponseDto;
import com.gabriel.ecommerce.dto.cart.CartResponseDto;
import com.gabriel.ecommerce.entity.Cart;
import com.gabriel.ecommerce.entity.CartItem;
import org.springframework.stereotype.Component;

@Component
public class CartMapper {

    public CartResponseDto toCartResponseDto(Cart cart) {
        return new CartResponseDto(
                cart.getId(),
                cart.getItems().stream()
                        .map(this::toItemDto)
                        .toList()
        );
    }

    private CartItemResponseDto toItemDto(CartItem item) {
        return new CartItemResponseDto(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getProduct().getDescription(),
                item.getProduct().getPrice(),
                item.getProduct().getStock(),
                item.getQuantity()
        );
    }
}
