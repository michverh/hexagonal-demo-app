package com.michel.hexagonaldemoapp.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.text.MessageFormat.format;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Order {
    private UUID uuid;
    private long userId;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDate orderDate;
    private Address shippingAddress;
    private Address billingAddress;
    private Optional<Discount> discount;
    private List<OrderItem> productsAndQuantities;


    public static Order withoutId(long userId, BigDecimal total, Address shippingAddress, Address billingAddress,
                                  Optional<Discount> discount, List<OrderItem> productsAndQuantities) {
        return new Order(UUID.randomUUID(), userId, total, OrderStatus.Pending, LocalDate.now(), shippingAddress,
                billingAddress, discount, productsAndQuantities);
    }

    public static Order withId(UUID uuid, long userId, BigDecimal total, OrderStatus status, LocalDate orderDate,
                               Address shippingAddress, Address billingAddress, Optional<Discount> discount,
                               List<OrderItem> productsAndQuantities) {
        return new Order(uuid, userId, total, status, orderDate, shippingAddress, billingAddress,
                discount, productsAndQuantities);
    }

    public void recalculateTotals() {
        final BigDecimal sum = productsAndQuantities
                .stream()
                .map(orderItem -> orderItem.price().multiply(BigDecimal.valueOf(orderItem.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        final BigDecimal subTotal = discount.map(discount -> discount.apply(sum)).orElse(sum);
        final BigDecimal shipping = calculateShippingCost(subTotal);
        totalAmount = subTotal.add(shipping);
    }

    private BigDecimal calculateShippingCost(final BigDecimal subTotal) {
        return subTotal.doubleValue() > 50.0
                ? BigDecimal.ZERO
                : new BigDecimal(5);
    }

    public boolean isCancellable() {
        return status.equals(OrderStatus.Processing);
    }

    public void cancel() {
        if (!isCancellable()) {
            throw new IllegalStateException(format("Order cannot be cancelled, due to status: {0}", status));
        }
        status = OrderStatus.Cancelled;
    }


}
