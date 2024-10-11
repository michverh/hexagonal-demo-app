package com.michel.hexagonaldemoapp.cucumber;

import com.michel.hexagonaldemoapp.domain.Address;
import com.michel.hexagonaldemoapp.domain.Order;
import com.michel.hexagonaldemoapp.domain.Product;
import com.michel.hexagonaldemoapp.domain.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SharedState {

    private long userId;
    private UUID productId = UUID.randomUUID();
    private UUID orderId = UUID.randomUUID();
    private Address shippingAddress;
    private Address billingAddress;
    private List<Product> products = new ArrayList<>();
    private ShoppingCart shoppingCart;
    private Order order;
    private Exception exception;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
