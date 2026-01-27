package com.gabriel.ecommerce.mapper;

import com.gabriel.ecommerce.dto.order.OrderItemResponseDto;
import com.gabriel.ecommerce.dto.order.OrderResponseDto;
import com.gabriel.ecommerce.entity.Order;
import com.gabriel.ecommerce.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponseDto toResponseDto(Order order) {
        return new OrderResponseDto(
                order.getId(),
                order.getStatus(),
                order.getTotal(),
                order.getCreatedAt(),
                toItemResponseDtoList(order.getItems())
        );
    }

    private List<OrderItemResponseDto> toItemResponseDtoList(List<OrderItem> items) {
        return items.stream()
                .map(this::toItemResponseDto)
                .collect(Collectors.toList());
    }

    private OrderItemResponseDto toItemResponseDto(OrderItem item) {
        BigDecimal subtotal = item.getPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity()));

        return new OrderItemResponseDto(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                subtotal
        );
    }
}
