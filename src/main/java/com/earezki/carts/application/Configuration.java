package com.earezki.carts.application;

import com.earezki.carts.domain.IdFactory;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;

@Dependent
public class Configuration {

    @Produces
    public IdFactory idFactory() {
        return new IdFactory();
    }

}
