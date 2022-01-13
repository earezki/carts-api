package com.earezki.carts.application;

import com.earezki.carts.domain.*;
import com.earezki.carts.domain.tenant.TenantId;
import com.earezki.carts.domain.tenant.TenantProvider;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Set;

@ApplicationScoped
public class CartApplicationService {

    private final IdFactory idFactory;
    private final CartRepository cartRepository;
    private final TenantProvider tenantProvider;

    public CartApplicationService(IdFactory idFactory,
                                  CartRepository cartRepository,
                                  TenantProvider tenantProvider) {
        this.idFactory = idFactory;
        this.cartRepository = cartRepository;
        this.tenantProvider = tenantProvider;
    }

    public Uni<String> createCart(CreateCartCommand command) {
        Cart newCart = new Cart(
                new CartId(idFactory),
                Price.of(BigDecimal.ZERO),
                Currency.getInstance(command.getCurrency().toUpperCase()),
                Set.of()
        );

        return tenantProvider.tenantOfId(new TenantId(command.getTenantId()))
                .flatMap(tenant ->
                        cartRepository.store(tenant, newCart)
                                .map(CartId::asString)
                );
    }

    public Uni<Void> addCartItem(AddCartItemCommand command) {
        CartId cartId = new CartId(command.getCartId());

        return tenantProvider.tenantOfId(new TenantId(command.getTenantId()))
                .flatMap(tenant ->
                        cartRepository.cartOfId(tenant, cartId)
                                .map(cart -> {
                                    cart.addItem(
                                            tenant,
                                            mergeMode(command),
                                            createCartItem(command)
                                    );
                                    return cart;
                                })
                                .flatMap(cart -> cartRepository.store(tenant, cart))
                                .replaceWithVoid()
                );
    }

    private MergeMode mergeMode(AddCartItemCommand command) {
        String mergeMode = command.getMergeMode();
        if (mergeMode == null) {
            return MergeMode.MERGE;
        }

        return MergeMode.valueOf(mergeMode.toUpperCase());
    }

    private CartItem createCartItem(AddCartItemCommand command) {
        return new CartItem(
                ProductId.of(command.getProductId()),
                Price.of(command.getPrice()),
                Quantity.of(command.getQuantity())
        );
    }

}
