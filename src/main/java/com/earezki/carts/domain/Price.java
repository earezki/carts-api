package com.earezki.carts.domain;

import com.earezki.carts.toolkit.Constraints;

import java.math.BigDecimal;
import java.util.Objects;

public class Price {

    private final BigDecimal amount;

    private Price(BigDecimal amount) {
        this.amount = amount;
    }

    public static Price of(BigDecimal amount) {
        new Constraints()
                .withNotNull(amount, "Amount shouldn't be null")
                .withNoneNegativeNumber(amount, "Amount should be positive")
                .validate();

        return new Price(amount);
    }

    public BigDecimal amount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(amount, price.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }
}
