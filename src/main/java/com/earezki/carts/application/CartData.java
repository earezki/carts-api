package com.earezki.carts.application;

import java.math.BigDecimal;
import java.util.List;

public class CartData {

    private String id;
    private BigDecimal totalPrice;
    private String currency;
    private List<CartItemData> cartItems;

    public CartData(String id, BigDecimal totalPrice, String currency, List<CartItemData> cartItems) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.currency = currency;
        this.cartItems = cartItems;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<CartItemData> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemData> cartItems) {
        this.cartItems = cartItems;
    }
}
