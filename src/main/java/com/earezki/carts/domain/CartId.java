package com.earezki.carts.domain;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class CartId {

    private final UUID id;

    public CartId(IdFactory idFactory) {
        this(idFactory.create());
    }

    public CartId(String id) {
        this(UUID.fromString(id));
    }

    private CartId(UUID id) {
        this.id = requireNonNull(
                id,
                "CartId shouldn't be null"
        );
    }

    public String asString() {
        return id.toString();
    }
}
