package com.earezki.carts.toolkit;

import java.math.BigDecimal;

public class NonNegativeNumber extends Constraint<Number> {

    NonNegativeNumber(Number number, String errorMessage) {
        super(number, errorMessage);
    }

    @Override
    boolean hasErrors() {
        if (value instanceof BigDecimal bigDecimal) {
            return bigDecimal.compareTo(BigDecimal.ZERO) < 0;
        }

        if (value instanceof Integer integer) {
            return integer < 0;
        }

        throw new IllegalArgumentException("Unsupported type [" + value.getClass() + "]");
    }
}
