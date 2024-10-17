package com.michel.hexagonaldemoapp.adapter.out.persistence.schema;

import com.michel.hexagonaldemoapp.domain.CartItem;
import com.michel.hexagonaldemoapp.domain.Product;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartItemValueObject {

    private UUID productId;
    private int quantity;

    public static CartItemValueObject fromDomain(CartItem cartItem) {
        return new CartItemValueObject(
                cartItem.getProduct().getId(),
                cartItem.getQuantity()
        );
    }

    public CartItem toDomain(Product product) {
        return new CartItem(quantity, product);
    }
}
