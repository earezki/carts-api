package com.earezki.carts.toolkit;

class NotBlankConstraint extends Constraint<String> {

    NotBlankConstraint(String text, String errorMessage) {
        super(text, errorMessage);
    }

    @Override
    boolean hasErrors() {
        return value.isBlank();
    }
}
