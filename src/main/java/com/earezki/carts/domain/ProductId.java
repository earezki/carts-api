package com.earezki.carts.domain;

import com.earezki.carts.toolkit.Constraints;

import java.util.Objects;

public class ProductId {

    private final String value;

    private ProductId(String value) {
        this.value = value;
    }

    public static ProductId of(String value) {
        new Constraints()
                .withNotNull(value, "Product id shouldn't be null")
                .validate();

        return new ProductId(value);
    }

    public String asString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductId productId = (ProductId) o;
        return Objects.equals(value, productId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
