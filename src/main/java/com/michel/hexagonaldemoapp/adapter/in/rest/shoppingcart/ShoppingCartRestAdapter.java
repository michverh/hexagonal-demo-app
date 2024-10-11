package com.michel.hexagonaldemoapp.adapter.in.rest.shoppingcart;

import com.michel.hexagonaldemoapp.adapter.in.rest.shoppingcart.model.AddProductsToShoppingCartRequest;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.command.AddProductsToShoppingCartCommand;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.AddProductsToShoppingCartUseCase;
import com.michel.hexagonaldemoapp.application.port.in.shoppingcart.GetShoppingCartUseCase;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("shoppingCart")
public class ShoppingCartRestAdapter {

    private final GetShoppingCartUseCase getShoppingCartUseCase;
    private final AddProductsToShoppingCartUseCase addProductsToShoppingCartUseCase;

    @GetMapping("/{id}")
    public ShoppingCart getShoppingCart(@PathVariable("id") long userId) {
        return getShoppingCartUseCase.getShoppingCart(userId);
    }

    @PostMapping("/{id}")
    public void addProductsToShoppingCart(@PathVariable("id") long userId, AddProductsToShoppingCartRequest request) {
        final AddProductsToShoppingCartCommand command
                = new AddProductsToShoppingCartCommand(userId, request.productsUUIDandQuantity());
        addProductsToShoppingCartUseCase.addProducts(command);
    }

}
