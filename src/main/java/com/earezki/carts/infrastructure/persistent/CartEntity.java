package com.earezki.carts.infrastructure.persistent;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(
        name = "CARTS",
        indexes = {
                @Index(columnList = "id, tenantId")
        }
)
public class CartEntity {

    @Id
    @Column(length = 40)
    public String id;

    @Column(length = 40)
    public String tenantId;

    @Column(nullable = false)
    public BigDecimal price;

    @Column(length = 8, nullable = false)
    public String currency;

    @OneToMany(
            fetch = FetchType.EAGER,
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    public List<CartItemEntity> cartItems;

    public CartEntity() {
    }

    public CartEntity(String id, String tenantId, BigDecimal price,
                      String currency, List<CartItemEntity> cartItems) {
        this.id = id;
        this.tenantId = tenantId;
        this.price = price;
        this.currency = currency;
        this.cartItems = cartItems;
    }

}
