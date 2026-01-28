package com.gabriel.ecommerce.exception;

public class OrderNotFoundException extends RuntimeException {

  public OrderNotFoundException(Long orderId) {
      super("Pedido com id " + orderId + " não encontrado.");
    }
}
