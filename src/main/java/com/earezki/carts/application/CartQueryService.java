package com.earezki.carts.application;

import com.earezki.carts.domain.CartId;
import com.earezki.carts.domain.CartRepository;
import com.earezki.carts.domain.tenant.TenantId;
import com.earezki.carts.domain.tenant.TenantProvider;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CartQueryService {

    private final CartRepository cartRepository;
    private final TenantProvider tenantProvider;

    public CartQueryService(CartRepository cartRepository,
                            TenantProvider tenantProvider) {
        this.cartRepository = cartRepository;
        this.tenantProvider = tenantProvider;
    }

    public Uni<CartData> cartOfId(String tenantId, String id) {
        CartId cartId = new CartId(id);

        return tenantProvider.tenantOfId(new TenantId(tenantId))
                .flatMap(tenant ->
                        cartRepository.cartOfId(tenant, cartId)
                                .map(new CartDataMapper())
                );
    }

}
