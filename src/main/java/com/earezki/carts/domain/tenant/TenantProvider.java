package com.earezki.carts.domain.tenant;

import io.smallrye.mutiny.Uni;

public interface TenantProvider {

    Uni<Tenant> tenantOfId(TenantId tenantId);

}
