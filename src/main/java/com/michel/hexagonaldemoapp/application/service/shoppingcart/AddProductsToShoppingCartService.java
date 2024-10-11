package com.michel.hexagonaldemoapp.application.service.shoppingcart;

import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.command.AddProductsToShoppingCartCommand;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.AddProductsToShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductsPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.GetShoppingCartPort;
import com.michel.hexagonaldemoapp.application.port.out.shoppingcart.UpdateShoppingCartPort;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class AddProductsToShoppingCartService implements AddProductsToShoppingCartUseCase {

    private final GetShoppingCartPort getShoppingCartPort;
    private final FindProductsPort findProductsPort;
    private final UpdateShoppingCartPort updateShoppingCartPort;

    @Override
    public void addProducts(final AddProductsToShoppingCartCommand command) {
        final ShoppingCart shoppingCart = getShoppingCartPort.getShoppingCartByUserID(command.userId());
        final List<UUID> productUUIDs = command.productsUUIDandQuantity().keySet().stream().toList();

        findProductsPort.findAllByUUIDs(productUUIDs).forEach(product -> {
            int quantity = command.productsUUIDandQuantity().get(product.getId());
            product.hasSufficientStock(quantity);
            shoppingCart.addItem(product, quantity);
        });

        updateShoppingCartPort.update(shoppingCart);
    }
}
