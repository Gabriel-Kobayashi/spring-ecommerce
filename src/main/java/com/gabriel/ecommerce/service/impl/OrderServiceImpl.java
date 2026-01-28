package com.gabriel.ecommerce.service.impl;

import com.gabriel.ecommerce.entity.*;
import com.gabriel.ecommerce.entity.enums.OrderStatus;
import com.gabriel.ecommerce.exception.EmptyCartException;
import com.gabriel.ecommerce.exception.OrderNotFoundException;
import com.gabriel.ecommerce.exception.PaymentNotAllowedException;
import com.gabriel.ecommerce.repository.OrderRepository;
import com.gabriel.ecommerce.service.CartService;
import com.gabriel.ecommerce.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;

    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    @Override
    public Order createOrder(User user) {

        Cart cart = cartService.getCartByUser(user);

        if (cart.getItems().isEmpty()) {
            throw new EmptyCartException();
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for (CartItem cartItem : cart.getItems()) {

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());

            order.getItems().add(orderItem);

            total = total.add(
                    cartItem.getProduct()
                            .getPrice()
                            .multiply(BigDecimal.valueOf(cartItem.getQuantity()))
            );
        }

        order.setTotal(total);

        cartService.clearCart(user);

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    public Order getOrderByIdAndUser(Long orderId, User user) {
        return orderRepository.findByIdAndUser(orderId, user)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    @Override
    public void validateOrderForPayment(Order orderId, User user) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PaymentNotAllowedException("Pedido não encontrado"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new PaymentNotAllowedException("Pedido não pertence ao usuário");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new PaymentNotAllowedException("Pedido não pode ser pago nesse status: " + order.getStatus());
        }
    }

}
