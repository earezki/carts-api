package com.earezki.carts.domain.tenant;

import java.util.Objects;
import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class TenantId {

    private final UUID id;

    public TenantId(String id) {
        this(UUID.fromString(id));
    }

    public TenantId(UUID id) {
        this.id = requireNonNull(
                id,
                "CartId shouldn't be null"
        );
    }

    public String asString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantId tenantId = (TenantId) o;
        return Objects.equals(id, tenantId.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
