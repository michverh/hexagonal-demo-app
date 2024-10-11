package com.michel.hexagonaldemoapp.application.port.out.product;

import com.michel.hexagonaldemoapp.domain.Product;

import java.util.List;
import java.util.UUID;

public interface FindProductsPort {

    List<Product> findAllByUUIDs(final List<UUID> uuids);

}
