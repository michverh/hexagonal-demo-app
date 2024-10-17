package com.michel.hexagonaldemoapp.adapter.out.persistence.schema;

import com.michel.hexagonaldemoapp.domain.Order;
import com.michel.hexagonaldemoapp.domain.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderJpaEntity {

    @Id
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "user_id", nullable = false)
    private long userId;

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private OrderStatus status;

    @Column(name = "order_date", nullable = false)
    private LocalDate orderDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "streetName", column = @Column(name = "shipping_street_name")),
            @AttributeOverride(name = "streetNumber", column = @Column(name = "shipping_street_number")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "shipping_zip_code")),
            @AttributeOverride(name = "city", column = @Column(name = "shipping_city")),
            @AttributeOverride(name = "country", column = @Column(name = "shipping_country"))
    })
    private AddressValueObject shippingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "streetName", column = @Column(name = "billing_street_name")),
            @AttributeOverride(name = "streetNumber", column = @Column(name = "billing_street_number")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "billing_zip_code")),
            @AttributeOverride(name = "city", column = @Column(name = "billing_city")),
            @AttributeOverride(name = "country", column = @Column(name = "billing_country"))
    })
    private AddressValueObject billingAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "percentage", column = @Column(name = "discount_percentage")),
            @AttributeOverride(name = "code", column = @Column(name = "discount_code")),
            @AttributeOverride(name = "validUntil", column = @Column(name = "discount_valid_until"))
    })
    private DiscountValueObject discount;

    @ElementCollection
    @CollectionTable(
            name = "order_items",
            joinColumns = @JoinColumn(name = "order_uuid")
    )
    private List<OrderItemValueObject> productsAndQuantities = new ArrayList<>();

    public Order toDomain() {
        return Order.withId(
                uuid,
                userId,
                totalAmount,
                status,
                orderDate,
                shippingAddress.toDomain(),
                billingAddress.toDomain(),
                discount != null ? Optional.of(discount.toDomain()) : Optional.empty(),
                productsAndQuantities.stream().map(OrderItemValueObject::toDomain).toList()
        );
    }

    public static OrderJpaEntity fromDomain(Order order) {
        if (order == null) return null;
        return new OrderJpaEntity(
                order.getUuid(),
                order.getUserId(),
                order.getTotalAmount(),
                order.getStatus(),
                order.getOrderDate(),
                AddressValueObject.fromDomain(order.getShippingAddress()),
                AddressValueObject.fromDomain(order.getBillingAddress()),
                DiscountValueObject.fromDomain(order.getDiscount()),
                order.getProductsAndQuantities().stream().map(OrderItemValueObject::fromDomain).toList()
        );
    }
}

