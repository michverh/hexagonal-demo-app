package com.michel.hexagonaldemoapp.domain;

public enum OrderStatus {
    Pending("Pending"),
    Processing("Processing"),
    Shipped("Shipped"),
    Delivered("Delivered"),
    Cancelled("Cancelled");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }
}
