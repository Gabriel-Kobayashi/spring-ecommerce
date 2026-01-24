package com.gabriel.ecommerce.dto.product;

import com.gabriel.ecommerce.entity.Product;

import java.math.BigDecimal;

public record ProductResponseDto(

        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stock
) {

    public static ProductResponseDto fromEntity(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}
