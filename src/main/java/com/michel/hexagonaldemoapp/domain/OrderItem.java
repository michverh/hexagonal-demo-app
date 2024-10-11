package com.michel.hexagonaldemoapp.domain;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItem(
    UUID productId,
    BigDecimal price,
    int quantity
) {
}
