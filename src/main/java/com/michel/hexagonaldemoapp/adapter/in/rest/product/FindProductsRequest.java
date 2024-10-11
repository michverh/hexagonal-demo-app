package com.michel.hexagonaldemoapp.adapter.in.rest.product;

public record FindProductsRequest(
        String name,
        String category,
        String price
) {
}
