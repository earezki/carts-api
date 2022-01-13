package com.earezki.carts.domain;

import com.earezki.carts.domain.tenant.Tenant;
import io.smallrye.mutiny.Uni;

public interface CartRepository {

    Uni<CartId> store(Tenant tenant, Cart cart);

    Uni<Cart> cartOfId(Tenant tenant, CartId cartId);

}
