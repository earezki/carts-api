package com.earezki.carts.domain;

import static java.lang.String.format;

public class CartCapacityExceedException extends RuntimeException {

    public CartCapacityExceedException(int itemsCount, int maxCartItems) {
        super(format(
                "Cart size [%s] exceeds max capacity [%s]",
                itemsCount, maxCartItems
        ));
    }

}
