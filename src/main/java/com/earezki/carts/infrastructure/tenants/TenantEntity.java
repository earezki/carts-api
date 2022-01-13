package com.earezki.carts.infrastructure.tenants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TENANTS")
public class TenantEntity {

    @Id
    @Column(length = 40)
    public String id;

    @Column(name = "CART_CAPACITY")
    public int cartCapacity;

}
