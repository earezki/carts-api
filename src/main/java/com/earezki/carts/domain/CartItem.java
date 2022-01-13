package com.earezki.carts.domain;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class CartItem {

    private final ProductId productId;
    private final Price price;
    private final Quantity quantity;

    public CartItem(ProductId productId, Price price, Quantity quantity) {
        this.productId = requireNonNull(productId, "Product id must not be null");
        this.price = requireNonNull(price, "Price must not be null");
        this.quantity = requireNonNull(quantity, "Quantity must not be null");
    }

    public ProductId productId() {
        return productId;
    }

    public Price price() {
        return price;
    }

    public Quantity quantity() {
        return quantity;
    }

    public CartItem mergeWith(MergeMode mergeMode, CartItem cartItem) {
        if (!hasProduct(cartItem)) {
            return cartItem;
        }

        Quantity newQuantity = mergeMode.apply(this.quantity, cartItem.quantity);

        return new CartItem(
                productId,
                cartItem.price,
                newQuantity
        );

    }

    private boolean hasProduct(CartItem cartItem) {
        return Objects.equals(productId, cartItem.productId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return Objects.equals(productId, cartItem.productId) &&
                Objects.equals(price, cartItem.price) &&
                Objects.equals(quantity, cartItem.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, price, quantity);
    }
}
