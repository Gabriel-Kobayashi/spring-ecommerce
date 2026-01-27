package com.gabriel.ecommerce.repository;

import com.gabriel.ecommerce.entity.Order;
import com.gabriel.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
