package com.earezki.carts.domain;

import java.util.UUID;

public class IdFactory {

    public UUID create() {
        return UUID.randomUUID();
    }

}
