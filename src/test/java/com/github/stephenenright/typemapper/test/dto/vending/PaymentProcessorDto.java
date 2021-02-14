package com.github.stephenenright.typemapper.test.dto.vending;

public class PaymentProcessorDto extends BaseDto {

    private String name;

    private PaymentGatewayDto gateway;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PaymentGatewayDto getGateway() {
        return gateway;
    }

    public void setGateway(PaymentGatewayDto gateway) {
        this.gateway = gateway;
    }

}
