package com.gabriel.ecommerce.service;

import com.gabriel.ecommerce.entity.Cart;
import com.gabriel.ecommerce.entity.User;

public interface CartService {

    Cart getCartByUser(User user);

    Cart addProduct(User user, Long productId, int quantity);

    Cart updateQuantity(User user, Long productId, int quantity);

    void removeProduct(User user, Long productId);

    void clearCart(User user);
}
