package com.gabriel.ecommerce.controller;

import com.gabriel.ecommerce.dto.order.OrderResponseDto;
import com.gabriel.ecommerce.entity.Order;
import com.gabriel.ecommerce.entity.User;
import com.gabriel.ecommerce.mapper.OrderMapper;
import com.gabriel.ecommerce.service.OrderService;
import com.gabriel.ecommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;
    private final UserService userService;

    public OrderController(OrderService orderService, OrderMapper orderMapper, UserService userService) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());

        Order order = orderService.createOrder(user);

        OrderResponseDto response = orderMapper.toResponseDto(order);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> getUserOrders(@AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());

        List<OrderResponseDto> response = orderService.getOrdersByUser(user)
                .stream()
                .map(orderMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable Long orderId, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByEmail(userDetails.getUsername());

        Order order = orderService.getOrderByIdAndUser(orderId, user);

        return ResponseEntity.ok(orderMapper.toResponseDto(order));
    }
}
