package com.earezki.carts.domain;

public enum MergeMode {

    REPLACE {
        public Quantity apply(Quantity lhs, Quantity rhs) {
            return rhs;
        }
    },

    MERGE {
        @Override
        public Quantity apply(Quantity lhs, Quantity rhs) {
            return lhs.add(rhs);
        }
    };

    public abstract Quantity apply(Quantity lhs, Quantity rhs);
}
