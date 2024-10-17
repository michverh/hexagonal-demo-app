package com.michel.hexagonaldemoapp.adapter.out.persistence.schema;

import com.michel.hexagonaldemoapp.domain.ShoppingCart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "shopping_carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCartJpaEntity {

    @Id
    @Column(name = "user_id", updatable = false, nullable = false)
    private long userId;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "sub_total", nullable = false)
    private BigDecimal subTotal;

    @Column(name = "shipping", nullable = false)
    private BigDecimal shipping;

    @ElementCollection
    @CollectionTable(
            name = "cart_items",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private List<CartItemValueObject> productsAndQuantities = new ArrayList<>();

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "percentage", column = @Column(name = "discount_percentage")),
            @AttributeOverride(name = "code", column = @Column(name = "discount_code")),
            @AttributeOverride(name = "validUntil", column = @Column(name = "discount_valid_until"))
    })
    private DiscountValueObject discount;

    public static ShoppingCartJpaEntity fromDomain(final ShoppingCart shoppingCart) {
        return new ShoppingCartJpaEntity(
                shoppingCart.getUserId(),
                shoppingCart.getTotal(),
                shoppingCart.getSubTotal(),
                shoppingCart.getShipping(),
                shoppingCart.getProductsAndQuantities().stream().map(CartItemValueObject::fromDomain).toList(),
                DiscountValueObject.fromDomain(shoppingCart.getDiscount())
        );
    }
}