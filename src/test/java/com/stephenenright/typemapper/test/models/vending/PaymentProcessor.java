package com.stephenenright.typemapper.test.models.vending;

public class PaymentProcessor extends BaseModel {

    private String name;

    private PaymentGateway gateway;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public void setGateway(PaymentGateway gateway) {
        this.gateway = gateway;
    }

}
