package com.earezki.carts.toolkit;

import java.util.ArrayList;
import java.util.List;

public class Constraints {

    private final List<Constraint<?>> constraints = new ArrayList<>();
    private final List<String> errors = new ArrayList<>();

    public Constraints withNotNull(Object o, String errorMessage) {
        this.constraints.add(
                new NotNullConstraint(o, errorMessage)
        );

        return this;
    }

    public Constraints withNotBlank(String value, String errorMessage) {
        this.constraints.add(
                new NotBlankConstraint(value, errorMessage)
        );

        return this;
    }

    public Constraints withNoneNegativeNumber(Number number, String errorMessage) {
        this.constraints.add(
                new NonNegativeNumber(number, errorMessage)
        );

        return this;
    }

    public void validate() {
        List<String> errors = this.constraints.stream()
                .filter(Constraint::hasErrors)
                .map(Constraint::errorMessage)
                .toList();

        if (!errors.isEmpty()) {
            throw new ConstraintViolatedException(errors);
        }
    }

}
