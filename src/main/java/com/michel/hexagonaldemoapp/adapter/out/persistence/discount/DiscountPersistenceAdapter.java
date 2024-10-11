package com.michel.hexagonaldemoapp.adapter.out.persistence.discount;

import com.michel.hexagonaldemoapp.application.port.out.discount.GetDiscountPort;
import com.michel.hexagonaldemoapp.domain.Discount;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DiscountPersistenceAdapter implements GetDiscountPort {

    @Override
    public Optional<Discount> getDiscount(String discountCode) {
        return Optional.empty();
    }
}
