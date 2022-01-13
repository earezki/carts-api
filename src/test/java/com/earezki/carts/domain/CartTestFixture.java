package com.earezki.carts.domain;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

class CartTestFixture {

    private final IdFactory idFactory = new IdFactory();
    private CartId id;
    private Price total;
    private Currency currency;
    private Set<CartItem> cartItems;

    CartTestFixture() {
        id = new CartId(idFactory);
        total = Price.of(BigDecimal.ONE);
        currency = Currency.getInstance("USD");
        cartItems = new HashSet<>();
    }

    public CartTestFixture withId(CartId id) {
        this.id = id;
        return this;
    }

    public CartTestFixture withTotal(Price total) {
        this.total = total;
        return this;
    }

    public CartTestFixture withCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public CartTestFixture withCartItem(CartItem cartItem) {
        this.cartItems.add(cartItem);
        return this;
    }

    Cart fixture() {
        return new Cart(
                id,
                total,
                currency,
                cartItems
        );
    }

}