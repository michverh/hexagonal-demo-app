package com.michel.hexagonaldemoapp.application.port.out.discount;

import com.michel.hexagonaldemoapp.domain.Discount;

import java.util.Optional;

public interface GetDiscountPort {

    Optional<Discount> getDiscount(String discountCode);
}
