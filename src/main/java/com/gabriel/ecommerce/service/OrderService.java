package com.gabriel.ecommerce.service;

import com.gabriel.ecommerce.entity.Order;
import com.gabriel.ecommerce.entity.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user);

    List<Order> getOrdersByUser(User user);

    Order getOrderByIdAndUser(Long orderId, User user);
}
