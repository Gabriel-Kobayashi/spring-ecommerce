package com.gabriel.ecommerce.exception.cart;

public class CartItemNotFoundException extends RuntimeException {

    public CartItemNotFoundException(Long productId) {
        super("Produto " + productId + " não encontrado no carrinho");
    }
}
