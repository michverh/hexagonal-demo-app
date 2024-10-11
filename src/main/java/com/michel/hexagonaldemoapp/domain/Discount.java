package com.michel.hexagonaldemoapp.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;

public record Discount(BigDecimal percentage, String code, OffsetDateTime validUntil) {

    public BigDecimal apply(BigDecimal amount) {
        final BigDecimal discountedAmount = (isApplicable())
                ? amount.subtract(amount.multiply(percentage.movePointLeft(2)))
                : amount;
        return discountedAmount.setScale(0, RoundingMode.HALF_UP);
    }

    private boolean isApplicable() {
        return validUntil.isAfter(OffsetDateTime.now());
    }

    public void verify() {
        if (!isApplicable()) throw new RuntimeException("Discount code is expired.");
    }
}
