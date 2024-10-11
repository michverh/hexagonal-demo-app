package com.michel.hexagonaldemoapp.application.port.out.product;

import com.michel.hexagonaldemoapp.application.port.in.product.query.ProductQuery;
import com.michel.hexagonaldemoapp.domain.Product;

import java.util.List;

public interface QueryProductPort {

    List<Product> findAll(final ProductQuery productQuery);
}
