package com.michel.hexagonaldemoapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
public class Product {

    @Getter private UUID id;
    @Getter private String name;
    @Getter private String category;
    @Getter private BigDecimal price;
    @Getter private int stockQuantity;

    public boolean hasSufficientStock(int quantity) {
        return stockQuantity >= quantity;
    }

    public void decreaseStock(int quantity) {
        if (!hasSufficientStock(quantity)) {
            throw new IllegalArgumentException("Not enough stock for Product: " + name);
        }
        stockQuantity -= quantity;
    }

    public void increaseStockQuantity(int quantity) {
        stockQuantity += quantity;
    }
}
