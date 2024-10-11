package com.michel.hexagonaldemoapp.application.port.in.shoppingcart.command;

public record ApplyDiscountCommand(long userId, String discountCode) {
}
