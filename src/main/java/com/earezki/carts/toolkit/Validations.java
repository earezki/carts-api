package com.earezki.carts.toolkit;

import java.util.function.Function;

public final class Validations {

    public static <T> T check(Function<T, Boolean> validator, T object, String errorMsg) {
        if (!validator.apply(object)) {
            throw new IllegalArgumentException(errorMsg);
        }

        return object;
    }

}
