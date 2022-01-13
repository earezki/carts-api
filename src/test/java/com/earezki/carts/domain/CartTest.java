package com.earezki.carts.domain;

import com.earezki.carts.domain.tenant.Tenant;
import com.earezki.carts.domain.tenant.TenantId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CartTest {

    @Test
    void test_add_new_item_on_empty_cart() {
        Cart cart = new CartTestFixture()
                .fixture();

        cart.addItem(
                new Tenant(new TenantId(UUID.randomUUID()), 2),
                MergeMode.MERGE,
                new CartItem(ProductId.of("123"), Price.of(BigDecimal.ONE), Quantity.of(1))
        );

        assertEquals(
                Set.of(
                        new CartItem(ProductId.of("123"), Price.of(BigDecimal.ONE), Quantity.of(1))
                ),
                cart.cartItems()
        );
    }

    @Test
    void test_merge_new_item_on_with_existing_cart_items() {
        Cart cart = new CartTestFixture()
                .withCartItem(new CartItem(ProductId.of("123"), Price.of(BigDecimal.ONE), Quantity.of(1)))
                .withCartItem(new CartItem(ProductId.of("124"), Price.of(BigDecimal.ONE), Quantity.of(1)))
                .fixture();

        cart.addItem(
                new Tenant(new TenantId(UUID.randomUUID()), 2),
                MergeMode.MERGE,
                new CartItem(ProductId.of("123"), Price.of(BigDecimal.ONE), Quantity.of(1))
        );

        assertEquals(
                Set.of(
                        new CartItem(ProductId.of("123"), Price.of(BigDecimal.ONE), Quantity.of(2)),
                        new CartItem(ProductId.of("124"), Price.of(BigDecimal.ONE), Quantity.of(1))
                ),
                cart.cartItems()
        );
    }

    @Test
    void test_replace_new_item_on_with_existing_cart_items() {
        Cart cart = new CartTestFixture()
                .withCartItem(new CartItem(ProductId.of("123"), Price.of(BigDecimal.ONE), Quantity.of(2)))
                .withCartItem(new CartItem(ProductId.of("124"), Price.of(BigDecimal.ONE), Quantity.of(1)))
                .fixture();

        cart.addItem(
                new Tenant(new TenantId(UUID.randomUUID()), 2),
                MergeMode.REPLACE,
                new CartItem(ProductId.of("123"), Price.of(BigDecimal.ONE), Quantity.of(1))
        );

        assertEquals(
                Set.of(
                        new CartItem(ProductId.of("123"), Price.of(BigDecimal.ONE), Quantity.of(1)),
                        new CartItem(ProductId.of("124"), Price.of(BigDecimal.ONE), Quantity.of(1))
                ),
                cart.cartItems()
        );
    }

    @Test
    void test_cart_shouldnt_accept_items_more_than_configured() {
        Cart cart = new CartTestFixture()
                .withCartItem(new CartItem(ProductId.of("123"), Price.of(BigDecimal.ONE), Quantity.of(2)))
                .withCartItem(new CartItem(ProductId.of("124"), Price.of(BigDecimal.ONE), Quantity.of(1)))
                .fixture();

        assertThrows(
                CartCapacityExceedException.class,
                () -> cart.addItem(
                        new Tenant(new TenantId(UUID.randomUUID()), 2),
                        MergeMode.REPLACE,
                        new CartItem(ProductId.of("125"), Price.of(BigDecimal.ONE), Quantity.of(1))
                )
        );
    }

}