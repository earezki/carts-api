package com.earezki.carts.application;

import javax.validation.constraints.NotBlank;

public class CreateCartCommand {

    private String tenantId;

    @NotBlank(message = "Currency shouldn't be blank")
    private String currency;

    public CreateCartCommand() {
    }

    public CreateCartCommand(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
