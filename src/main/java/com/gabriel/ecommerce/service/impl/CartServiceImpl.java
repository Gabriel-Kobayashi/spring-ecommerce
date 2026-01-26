package com.gabriel.ecommerce.service.impl;

import com.gabriel.ecommerce.entity.Cart;
import com.gabriel.ecommerce.entity.CartItem;
import com.gabriel.ecommerce.entity.Product;
import com.gabriel.ecommerce.entity.User;
import com.gabriel.ecommerce.exception.ProductNotFoundException;
import com.gabriel.ecommerce.repository.CartItemRepository;
import com.gabriel.ecommerce.repository.CartRepository;
import com.gabriel.ecommerce.repository.ProductRepository;
import com.gabriel.ecommerce.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    private Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });
    }

    @Override
    public Cart getCartByUser(User user) {
        return getOrCreateCart(user);
    }

    @Override
    public Cart addProduct(User user, Long productId, int quantity) {

        Cart cart = getOrCreateCart(user);

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        }
        else {
            CartItem item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
            cart.getItems().add(item);
        }

        return cartRepository.save(cart);
    }

    @Override
    public Cart updateQuantity(User user, Long productId, int quantity) {

        Cart cart = getOrCreateCart(user);

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Item não encontrado no carrinho"));

        if (quantity <= 0) {
            cart.getItems().remove(item);
        }
        else {
            item.setQuantity(quantity);
        }

        return cartRepository.save(cart);
    }

    @Override
    public void removeProduct(User user, Long productId) {

        Cart cart = getOrCreateCart(user);

        cart.getItems().removeIf(
                item -> item.getProduct().getId().equals(productId)
        );

        cartRepository.save(cart);
    }

    @Override
    public void clearCart(User user) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
