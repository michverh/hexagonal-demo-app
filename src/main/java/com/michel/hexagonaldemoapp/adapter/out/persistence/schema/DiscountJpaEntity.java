package com.michel.hexagonaldemoapp.adapter.out.persistence.schema;


import com.michel.hexagonaldemoapp.domain.Discount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(name = "percentage", nullable = false)
    private BigDecimal percentage;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "valid_until", nullable = false)
    private OffsetDateTime validUntil;

    public Discount toDomain() {
        return new Discount(percentage, code, validUntil);
    }
}
