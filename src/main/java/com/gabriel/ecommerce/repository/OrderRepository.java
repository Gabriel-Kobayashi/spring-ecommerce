package com.gabriel.ecommerce.repository;

import com.gabriel.ecommerce.entity.Order;
import com.gabriel.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndUser(Long id, User user);

    List<Order> findByUser(User user);

}
