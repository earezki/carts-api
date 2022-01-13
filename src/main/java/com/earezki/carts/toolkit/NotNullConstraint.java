package com.earezki.carts.toolkit;

class NotNullConstraint extends Constraint<Object> {

    NotNullConstraint(Object value, String errorMessage) {
        super(value, errorMessage);
    }

    @Override
    boolean hasErrors() {
        return value == null;
    }

}
