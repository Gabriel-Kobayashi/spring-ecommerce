package com.gabriel.ecommerce.dto.order;

import java.math.BigDecimal;

public record OrderItemResponseDto(
        Long productId,
        String productName,
        int quantity,
        BigDecimal price,
        BigDecimal subtotal
) {
}
