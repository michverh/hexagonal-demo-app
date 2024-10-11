package com.michel.hexagonaldemoapp.adapter.in.rest.order;

import com.michel.hexagonaldemoapp.adapter.in.rest.order.model.PlaceOrderRequest;
import com.michel.hexagonaldemoapp.application.port.in.order.command.PlaceOrderCommand;
import com.michel.hexagonaldemoapp.application.port.in.order.PlaceOrderUseCase;
import com.michel.hexagonaldemoapp.application.port.in.order.ViewOrderHistoryUseCase;
import com.michel.hexagonaldemoapp.application.port.in.order.ViewOrderUseCase;
import com.michel.hexagonaldemoapp.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("order")
public class OrderRestAdapter {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final ViewOrderUseCase viewOrderUseCase;
    private final ViewOrderHistoryUseCase viewOrderHistoryUseCase;

    @PostMapping
    public void placeOrder(@RequestBody final PlaceOrderRequest request) {
        final PlaceOrderCommand command
                = new PlaceOrderCommand(request.shippingAddress(), request.billingAddress(), request.shoppingCart());
        placeOrderUseCase.placeOrder(command);
    }

    @GetMapping("/{orderId}")
    public Order viewOrder(@PathVariable final String orderId) {
        return viewOrderUseCase.findOrder(UUID.fromString(orderId));
    }

    @GetMapping("/{userId}/history")
    public List<Order> viewOrder(@PathVariable final long userId) {
        return viewOrderHistoryUseCase.viewOrderHistory(userId);
    }

}
