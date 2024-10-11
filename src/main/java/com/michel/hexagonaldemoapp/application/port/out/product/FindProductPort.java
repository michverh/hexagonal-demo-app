package com.michel.hexagonaldemoapp.application.port.out.product;

import com.michel.hexagonaldemoapp.domain.Product;

import java.util.Optional;
import java.util.UUID;

public interface FindProductPort {

    Optional<Product> findByUuid(final UUID uuid);

}
