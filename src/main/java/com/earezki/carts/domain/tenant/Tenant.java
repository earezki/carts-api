package com.earezki.carts.domain.tenant;

public class Tenant {

    private TenantId id;
    private int maxCartItems;

    public Tenant(TenantId id, int maxCartItems) {
        this.id = id;
        this.maxCartItems = maxCartItems;
    }

    public TenantId id() {
        return id;
    }

    public boolean exceedsCapacity(int itemsCount) {
        return itemsCount > maxCartItems;
    }

    public int maxCartItems() {
        return maxCartItems;
    }
}
