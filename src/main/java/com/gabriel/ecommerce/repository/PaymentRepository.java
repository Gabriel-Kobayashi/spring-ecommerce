package com.gabriel.ecommerce.repository;

import com.gabriel.ecommerce.entity.Order;
import com.gabriel.ecommerce.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrder(Order order);
}
