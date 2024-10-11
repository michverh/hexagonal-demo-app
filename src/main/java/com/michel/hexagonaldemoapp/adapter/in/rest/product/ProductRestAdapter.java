package com.michel.hexagonaldemoapp.adapter.in.rest.product;

import com.michel.hexagonaldemoapp.application.port.in.product.FindProductsUseCase;
import com.michel.hexagonaldemoapp.application.port.in.product.query.ProductQuery;
import com.michel.hexagonaldemoapp.domain.Product;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
public class ProductRestAdapter {

    private final FindProductsUseCase findProductsUseCase;

    @GetMapping
    public List<Product> findProducts(@RequestBody FindProductsRequest request) {
        final ProductQuery query = new ProductQuery(request.name(), request.category(), request.price());
        return findProductsUseCase.find(query);
    }
}
