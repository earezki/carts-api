package com.earezki.carts.toolkit;

import java.util.List;

public class ConstraintViolatedException extends RuntimeException {

    private final List<String> errors;

    public ConstraintViolatedException(List<String> errors) {
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

}
