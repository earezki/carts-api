package com.earezki.carts.domain;

import com.earezki.carts.domain.tenant.Tenant;
import com.earezki.carts.toolkit.Constraints;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class Cart {

    private CartId id;
    private Price total;
    private Currency currency;
    private Set<CartItem> cartItems;

    public Cart(CartId id, Price total, Currency currency, Set<CartItem> cartItems) {
        new Constraints()
                .withNotNull(id, "Id shouldn't be null")
                .withNotNull(total, "Total price shouldn't be null")
                .withNotNull(currency, "Currency shouldn't be null")
                .withNotNull(cartItems, "Cart items shouldn't be null")
                .validate();

        this.id = id;
        this.total = total;
        this.currency = currency;
        this.cartItems = Set.copyOf(cartItems);
    }

    public CartId id() {
        return id;
    }

    public Price total() {
        return total;
    }

    public Currency currency() {
        return currency;
    }

    public Set<CartItem> cartItems() {
        return cartItems;
    }

    public void addItem(Tenant tenant,
                        MergeMode mergeMode,
                        CartItem cartItem) {
        Map<ProductId, CartItem> itemsByProductId = itemsByProductId();

        itemsByProductId.merge(
                cartItem.productId(),
                cartItem,
                (existingItem, newItem) -> existingItem.mergeWith(mergeMode, newItem)
        );

        Collection<CartItem> newCartItems = itemsByProductId.values();

        if (tenant.exceedsCapacity(newCartItems.size())) {
            throw new CartCapacityExceedException(
                    newCartItems.size(),
                    tenant.maxCartItems()
            );
        }

        this.cartItems = Set.copyOf(newCartItems);
    }

    private Map<ProductId, CartItem> itemsByProductId() {
        return this.cartItems.stream()
                .collect(toMap(
                        CartItem::productId,
                        Function.identity()
                ));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cart cart = (Cart) o;
        return id.equals(cart.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
