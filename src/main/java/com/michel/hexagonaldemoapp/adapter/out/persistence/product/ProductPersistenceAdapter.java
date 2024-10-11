package com.michel.hexagonaldemoapp.adapter.out.persistence.product;

import com.michel.hexagonaldemoapp.application.port.in.product.query.ProductQuery;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductPort;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductsPort;
import com.michel.hexagonaldemoapp.application.port.out.product.QueryProductPort;
import com.michel.hexagonaldemoapp.domain.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductPersistenceAdapter implements FindProductPort, FindProductsPort, QueryProductPort {

    @Override
    public Optional<Product> findByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAllByUUIDs(List<UUID> uuids) {
        return List.of();
    }

    @Override
    public List<Product> findAll(ProductQuery productQuery) {
        return List.of();
    }
}
