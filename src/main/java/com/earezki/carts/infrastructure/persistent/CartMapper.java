package com.earezki.carts.infrastructure.persistent;

import com.earezki.carts.domain.*;

import java.util.Currency;
import java.util.function.Function;

import static java.util.stream.Collectors.toSet;

class CartMapper implements Function<CartEntity, Cart> {

    @Override
    public Cart apply(CartEntity entity) {
        return new Cart(
                new CartId(entity.id),
                Price.of(entity.price),
                Currency.getInstance(entity.currency),
                entity.cartItems.stream()
                        .map(this::cartItem)
                        .collect(toSet())
        );
    }

    private CartItem cartItem(CartItemEntity item) {
        return new CartItem(ProductId.of(item.productId),
                Price.of(item.price),
                Quantity.of(item.quantity));
    }

}
