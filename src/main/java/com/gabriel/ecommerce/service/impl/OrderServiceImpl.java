package com.gabriel.ecommerce.service.impl;

import com.gabriel.ecommerce.entity.*;
import com.gabriel.ecommerce.entity.enums.OrderStatus;
import com.gabriel.ecommerce.entity.enums.PaymentStatus;
import com.gabriel.ecommerce.exception.EmptyCartException;
import com.gabriel.ecommerce.exception.OrderNotFoundException;
import com.gabriel.ecommerce.exception.PaymentNotAllowedException;
import com.gabriel.ecommerce.repository.OrderRepository;
import com.gabriel.ecommerce.repository.PaymentRepository;
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
    private final PaymentRepository paymentRepository;

    public OrderServiceImpl(OrderRepository orderRepository, CartService cartService, PaymentRepository paymentRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.paymentRepository = paymentRepository;
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

        Order savedOrder = orderRepository.save(order);

        Payment payment = new Payment();
        payment.setOrder(savedOrder);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setAmount(savedOrder.getTotal());

        paymentRepository.save(payment);

        cartService.clearCart(user);

        return savedOrder;
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
    public void validateOrderForPayment(Long orderId, User user) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PaymentNotAllowedException("Pedido não encontrado"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new PaymentNotAllowedException("Pedido não pertence ao usuário");
        }

        if (order.getStatus() != OrderStatus.CREATED) {
            throw new PaymentNotAllowedException("Pedido não pode ser pago nesse status: " + order.getStatus());
        }
    }

    @Override
    @Transactional
    public Order payOrder(Long orderId, User user) {

        validateOrderForPayment(orderId, user);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));

        Payment payment = paymentRepository.findByOrder(order)
                .orElseThrow(() -> new PaymentNotAllowedException("Pagamento não encontrado para o pedido"));

        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());

        order.setStatus(OrderStatus.PAID);
        order.setCreatedAt(LocalDateTime.now());

        paymentRepository.save(payment);

        return orderRepository.save(order);
    }

}
