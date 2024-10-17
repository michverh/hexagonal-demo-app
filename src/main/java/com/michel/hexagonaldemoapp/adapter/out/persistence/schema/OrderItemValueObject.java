package com.michel.hexagonaldemoapp.adapter.out.persistence.schema;

import com.michel.hexagonaldemoapp.domain.OrderItem;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemValueObject {

    private UUID productId;
    private BigDecimal price;
    private int quantity;

    public OrderItem toDomain() {
        return new OrderItem(productId, price, quantity);
    }

    public static OrderItemValueObject fromDomain(OrderItem orderItem) {
        if(orderItem == null) return null;
        return new OrderItemValueObject(orderItem.productId(), orderItem.price(), orderItem.quantity());
    }
}
