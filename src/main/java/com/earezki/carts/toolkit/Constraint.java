package com.earezki.carts.toolkit;

public abstract class Constraint<T> {

    protected final T value;
    private final String errorMessage;

    public Constraint(T value, String errorMessage) {
        this.value = value;
        this.errorMessage = errorMessage;
    }

    abstract boolean hasErrors();

    String errorMessage() {
        return errorMessage;
    }

}
