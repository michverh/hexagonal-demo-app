package com.michel.hexagonaldemoapp.application.service.order;

import com.michel.hexagonaldemoapp.application.port.in.order.command.PlaceOrderCommand;
import com.michel.hexagonaldemoapp.application.port.in.order.PlaceOrderUseCase;
import com.michel.hexagonaldemoapp.application.port.out.product.FindProductPort;
import com.michel.hexagonaldemoapp.application.port.out.order.PlaceOrderPort;
import com.michel.hexagonaldemoapp.domain.CartItem;
import com.michel.hexagonaldemoapp.domain.Order;
import com.michel.hexagonaldemoapp.domain.OrderItem;
import com.michel.hexagonaldemoapp.domain.Product;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {

    private final FindProductPort findProductPort;
    private final PlaceOrderPort placeOrderPort;

    @Override
    public void placeOrder(final PlaceOrderCommand command) {
        final List<OrderItem> orderItems = command.shoppingCart().getProductsAndQuantities()
                .stream()
                .map(this::getOrderItem)
                .toList();

        command.shoppingCart().recalculateTotals();

        final Order order = Order.withoutId(
                command.shoppingCart().getUserId(),
                command.shoppingCart().getTotal(),
                command.shippingAddress(),
                command.billingAddress(),
                command.shoppingCart().getDiscount(),
                orderItems
        );

        order.recalculateTotals();

        placeOrderPort.placeOrder(order);
    }

    private OrderItem getOrderItem(final CartItem cartItem) {
        final Product product = findProductPort.findByUuid(cartItem.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found: " + cartItem.getProduct().getName()));
        product.decreaseStock(cartItem.getQuantity());
        return new OrderItem(product.getId(), product.getPrice(), cartItem.getQuantity());
    }
}
