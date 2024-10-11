package com.michel.hexagonaldemoapp.application.port.in.shoppingcart.command;

import java.util.Map;
import java.util.UUID;

public record AddProductsToShoppingCartCommand(
        long userId,
        Map<UUID, Integer> productsUUIDandQuantity
) {}
