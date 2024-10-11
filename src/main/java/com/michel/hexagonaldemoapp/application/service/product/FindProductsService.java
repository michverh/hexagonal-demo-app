package com.michel.hexagonaldemoapp.application.service.product;

import com.michel.hexagonaldemoapp.application.port.in.product.FindProductsUseCase;
import com.michel.hexagonaldemoapp.application.port.in.product.query.ProductQuery;
import com.michel.hexagonaldemoapp.application.port.out.product.QueryProductPort;
import com.michel.hexagonaldemoapp.domain.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class FindProductsService implements FindProductsUseCase {

    private final QueryProductPort queryProductPort;

    @Override
    public List<Product> find(ProductQuery productQuery) {
        return queryProductPort.findAll(productQuery);
    }
}
