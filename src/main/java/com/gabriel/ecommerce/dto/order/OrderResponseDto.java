package com.gabriel.ecommerce.dto.order;

import com.gabriel.ecommerce.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDto(
        Long id,
        OrderStatus status,
        BigDecimal total,
        LocalDateTime createdAt,
        List<OrderItemResponseDto> items
) {
}
