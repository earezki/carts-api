package com.earezki.carts.domain;

import com.earezki.carts.toolkit.Constraints;

import java.util.Objects;

public class Quantity {

    private final int value;

    private Quantity(int value) {
        this.value = value;
    }

    public static Quantity of(int value) {
        new Constraints()
                .withNoneNegativeNumber(value,"Quantity should be positive")
                .validate();

        return new Quantity(value);
    }

    public int asInt() {
        return value;
    }

    public Quantity add(Quantity rhs) {
        return of(this.value + rhs.value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
