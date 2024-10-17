package com.michel.hexagonaldemoapp.adapter.out.persistence;

import com.michel.hexagonaldemoapp.adapter.out.persistence.schema.ProductJpaEntity;
import com.michel.hexagonaldemoapp.application.port.in.product.query.ProductQuery;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductPort;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductsPort;
import com.michel.hexagonaldemoapp.application.port.out.product.QueryProductPort;
import com.michel.hexagonaldemoapp.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ProductPersistenceAdapter implements FindProductPort, FindProductsPort, QueryProductPort {

    private final ProductRepository productRepository;

    @Override
    public Optional<Product> findByUuid(UUID uuid) {
        return productRepository.findById(uuid).map(ProductJpaEntity::toDomain);
    }

    @Override
    public List<Product> findAllByUUIDs(List<UUID> uuids) {
        return productRepository.findAllById(uuids).stream().map(ProductJpaEntity::toDomain).toList();
    }

    @Override
    public List<Product> findAll(ProductQuery productQuery) {
        return productRepository.findAllByQuery(
                productQuery.name(),
                BigDecimal.valueOf(Long.parseLong(productQuery.price())),
                productQuery.category()
        ).stream().map(ProductJpaEntity::toDomain).toList();
    }
}
