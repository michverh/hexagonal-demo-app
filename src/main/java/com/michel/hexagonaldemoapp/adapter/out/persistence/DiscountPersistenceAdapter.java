package com.michel.hexagonaldemoapp.adapter.out.persistence;

import com.michel.hexagonaldemoapp.application.port.out.discount.GetDiscountPort;
import com.michel.hexagonaldemoapp.domain.Discount;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class DiscountPersistenceAdapter implements GetDiscountPort {

    private final DiscountRepository discountRepository;

    @Override
    public Optional<Discount> getDiscount(String discountCode) {
        return discountRepository.findByCode(discountCode);
    }
}
