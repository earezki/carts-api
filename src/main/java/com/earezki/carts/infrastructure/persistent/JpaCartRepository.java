package com.earezki.carts.infrastructure.persistent;

import com.earezki.carts.domain.Cart;
import com.earezki.carts.domain.CartId;
import com.earezki.carts.domain.CartItem;
import com.earezki.carts.domain.CartRepository;
import com.earezki.carts.domain.tenant.Tenant;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@ApplicationScoped
public class JpaCartRepository implements
        CartRepository,
        PanacheRepositoryBase<CartEntity, String> {

    @Override
    public Uni<CartId> store(Tenant tenant, Cart cart) {
        return Panache.withTransaction(
                        () -> {
                            Uni<CartEntity> existingCartEntity = findById(
                                    tenant.id().asString(), cart.id().asString()
                            );

                            return existingCartEntity
                                    .replaceIfNullWith(CartEntity::new)
                                    .flatMap(cartEntity -> persist(toCartEntity(tenant, cart, cartEntity)));
                        })
                .map(persisted -> new CartId(persisted.id));
    }

    private Uni<CartEntity> findById(String tenantId, String cartId) {
        return find("""
                        FROM CartEntity cart
                        LEFT JOIN FETCH cart.cartItems
                        WHERE cart.id = ?1 AND cart.tenantId = ?2
                        """,
                cartId, tenantId)
                .firstResult();
    }

    private CartEntity toCartEntity(Tenant tenant, Cart cart, CartEntity entity) {
        if (entity.id == null) {
            entity.id = cart.id().asString();
            entity.tenantId = tenant.id().asString();
        }

        entity.price = cart.total().amount();
        entity.currency = cart.currency().getCurrencyCode();

        List<CartItemEntity> newCartItems = toCartItemEntities(cart);

        if (entity.cartItems == null) {
            entity.cartItems = newCartItems;
        } else {
            entity.cartItems.removeIf(
                    item -> newCartItems.stream()
                            .anyMatch(newItem -> newItem.productId.equals(item.productId))
            );

            entity.cartItems.addAll(newCartItems);
        }

        return entity;
    }

    private List<CartItemEntity> toCartItemEntities(Cart cart) {
        return cart.cartItems()
                .stream()
                .map(this::toCartItemEntity)
                .toList();
    }

    private CartItemEntity toCartItemEntity(CartItem item) {
        return new CartItemEntity(
                item.productId().asString(),
                item.price().amount(),
                item.quantity().asInt()
        );
    }

    @Override
    public Uni<Cart> cartOfId(Tenant tenant, CartId cartId) {
        Uni<CartEntity> cartEntity = findById(
                tenant.id().asString(), cartId.asString()
        );

        return cartEntity
                .onItem()
                .ifNull()
                .failWith(() -> new NoSuchElementException(format("No cart found with id %s", cartId.asString())))
                .map(new CartMapper());
    }


}
