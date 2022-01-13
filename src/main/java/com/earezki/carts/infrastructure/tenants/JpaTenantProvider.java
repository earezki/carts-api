package com.earezki.carts.infrastructure.tenants;

import com.earezki.carts.domain.tenant.Tenant;
import com.earezki.carts.domain.tenant.TenantId;
import com.earezki.carts.domain.tenant.TenantProvider;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.NoSuchElementException;

@ApplicationScoped
public class JpaTenantProvider implements
        TenantProvider,
        PanacheRepositoryBase<TenantEntity, String> {

    @Override
    public Uni<Tenant> tenantOfId(TenantId tenantId) {
        return findById(tenantId.asString())
                .map(this::fromEntity)
                .onItem()
                .ifNull()
                .failWith(() -> new NoSuchElementException("No tenant found with id: " + tenantId.asString()));
    }

    private Tenant fromEntity(TenantEntity entity) {
        return new Tenant(
                new TenantId(entity.id),
                entity.cartCapacity
        );
    }

}
