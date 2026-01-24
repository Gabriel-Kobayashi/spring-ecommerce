package com.gabriel.ecommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequestDto(

        @NotBlank(message="Nome do produto é obrigatório")
        String name,

        String description,

        @NotNull(message="Preço é obrigatório")
        @Positive(message="Valor deve ser maior que zero")
        BigDecimal price,

        @NotNull(message="Estoque é obrigatório")
        @PositiveOrZero(message="Estoque não pode ser negativo")
        Integer stock
) {
}
