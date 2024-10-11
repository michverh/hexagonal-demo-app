package com.michel.hexagonaldemoapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class CartItem {

    private int quantity;
    private Product product;

    public BigDecimal totalPrice() {
        return BigDecimal.valueOf(quantity).multiply(product.getPrice());
    }
}
