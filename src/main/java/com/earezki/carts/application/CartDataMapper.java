package com.earezki.carts.application;

import com.earezki.carts.domain.Cart;
import com.earezki.carts.domain.CartItem;

import java.util.function.Function;

class CartDataMapper implements Function<Cart, CartData> {

    @Override
    public CartData apply(Cart cart) {
        return new CartData(
                cart.id().asString(),
                cart.total().amount(),
                cart.currency().getCurrencyCode(),
                cart.cartItems().stream()
                        .map(this::cartItemData)
                        .toList()
        );
    }

    private CartItemData cartItemData(CartItem item) {
        return new CartItemData(
                item.productId().asString(),
                item.price().amount(),
                item.quantity().asInt()
        );
    }
}