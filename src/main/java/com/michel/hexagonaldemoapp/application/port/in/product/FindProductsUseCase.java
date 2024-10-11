package com.michel.hexagonaldemoapp.application.port.in.product;

import com.michel.hexagonaldemoapp.application.port.in.product.query.ProductQuery;
import com.michel.hexagonaldemoapp.domain.Product;

import java.util.List;

public interface FindProductsUseCase {

    List<Product> find(final ProductQuery productQuery);
}
