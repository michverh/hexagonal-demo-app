package com.michel.hexagonaldemoapp.cucumber.usecases.products;


import com.michel.hexagonaldemoapp.application.port.in.product.FindProductsUseCase;
import com.michel.hexagonaldemoapp.application.port.in.product.query.ProductQuery;
import com.michel.hexagonaldemoapp.application.port.out.product.QueryProductPort;
import com.michel.hexagonaldemoapp.application.service.product.FindProductsService;
import com.michel.hexagonaldemoapp.domain.Product;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class FindProductsUseCaseSteps {

    private static class FakeFindProductsAdapter implements QueryProductPort {

        public List<Product> findAll(ProductQuery productQuery) {
            List<Product> productList = new ArrayList<>();
            var product = new Product(UUID.randomUUID(), "LGHDTV", "Electronics", BigDecimal.valueOf(Long.parseLong("100")), 10);
            productList.add(product);
            return productList;
        }
    }

    QueryProductPort queryProductPort = new FakeFindProductsAdapter();
    FindProductsUseCase findProductsUseCase = new FindProductsService(queryProductPort);
    List<Product> products = new ArrayList<>();

    @Given("I request products with the following attributes")
    public void i_request_products_with_the_following_attributes(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);
        var productQuery = new ProductQuery(row.get("productName"), row.get("category"), null);

        products = findProductsUseCase.find(productQuery);
    }

    @Then("I receive the following products")
    public void i_receive_the_following_products(DataTable dataTable) {
        final Map<String, String> row = dataTable.asMaps().get(0);

        assertThat(products).isNotEmpty().hasSize(1);

        var actualProduct = products.get(0);

        assertThat(actualProduct.getName()).isEqualTo(row.get("productName"));
        assertThat(actualProduct.getCategory()).isEqualTo(row.get("category"));
        assertThat(actualProduct.getPrice().toString()).isEqualTo(row.get("price"));
    }

}
