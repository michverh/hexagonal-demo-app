package com.michel.hexagonaldemoapp.adapter.in.rest.shoppingcart.model;

import java.util.Map;
import java.util.UUID;

public record AddProductsToShoppingCartRequest(Map<UUID, Integer> productsUUIDandQuantity) {
}
