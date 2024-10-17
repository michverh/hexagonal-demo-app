package com.michel.hexagonaldemoapp.adapter.out.persistence.schema;

import com.michel.hexagonaldemoapp.domain.Discount;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Optional;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountValueObject {

    private BigDecimal percentage;
    private String code;
    private OffsetDateTime validUntil;

    public Discount toDomain() {
        return new Discount(percentage, code, validUntil);
    }

    public static DiscountValueObject fromDomain(final Optional<Discount> discountOptional) {
        return discountOptional.map(discount ->
            new DiscountValueObject(discount.percentage(), discount.code(), discount.validUntil()))
                .orElse(null);
    }
}
