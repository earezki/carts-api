package com.earezki.carts.application;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class AddCartItemCommand {

    private String cartId;
    @NotBlank(message = "ProductId shouldn't be blank")
    private String productId;
    @NotNull(message = "Price shouldn't be null")
    @Min(value = 0, message = "Price shouldn't be negative")
    private BigDecimal price;
    @NotNull(message = "Quantity shouldn't be null")
    @Min(value = 0, message = "Quantity shouldn't be negative")
    private Integer quantity;
    private String mergeMode;

    private String tenantId;

    public AddCartItemCommand() {
    }

    public AddCartItemCommand(String cartId, String productId, BigDecimal price, Integer quantity, String mergeMode) {
        this.cartId = cartId;
        this.productId = productId;
        this.price = price;
        this.quantity = quantity;
        this.mergeMode = mergeMode;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getMergeMode() {
        return mergeMode;
    }

    public void setMergeMode(String mergeMode) {
        this.mergeMode = mergeMode;
    }
}
