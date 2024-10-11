package com.michel.hexagonaldemoapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Getter
@AllArgsConstructor
public class ShoppingCart {

    private long userId;
    private BigDecimal total;
    private BigDecimal subTotal;
    private BigDecimal shipping;
    private List<CartItem> productsAndQuantities;
    private Optional<Discount> discount;

    public void addItem(Product product, int quantity) {
        if(!product.hasSufficientStock(quantity)) {
            throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
        }
        productsAndQuantities.add(new CartItem(quantity, product));

        recalculateTotals();
    }

    public void recalculateTotals() {
        final BigDecimal sum = getProductsAndQuantities()
                .stream()
                .map(CartItem::totalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        subTotal = discount.map(discount -> discount.apply(sum)).orElse(sum);
        shipping = calculateShippingCost();
        total = subTotal.add(shipping);
    }

    private BigDecimal calculateShippingCost() {
        return subTotal.doubleValue() > 50.0
                ? BigDecimal.ZERO
                : new BigDecimal(5);
    }

    public void applyDiscount(Optional<Discount> discount) {
        this.discount = discount;
        recalculateTotals();
    }
}
