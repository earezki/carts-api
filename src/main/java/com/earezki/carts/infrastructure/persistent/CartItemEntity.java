package com.earezki.carts.infrastructure.persistent;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CART_ITEMS")
public class CartItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String productId;

    @Column(nullable = false)
    public BigDecimal price;

    @Column(nullable = false)
    public int quantity;

    public CartItemEntity() {
    }

    public CartItemEntity(String productId, BigDecimal price, int quantity) {
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
    }

}
