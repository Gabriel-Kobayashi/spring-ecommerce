package com.gabriel.ecommerce.exception;

public class EmptyCartException extends IllegalArgumentException {

    public EmptyCartException() {
        super("Carrinho vazio. Adicione produtos antes de finalizar o pedido.");
    }
}
