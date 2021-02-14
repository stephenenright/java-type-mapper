package com.github.stephenenright.typemapper.test.models.vending;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class VendingMachine extends BaseModel {

    private String name;
    private List<Slot> slots = new LinkedList<Slot>();
    private PaymentProcessor processor;
    private VendingMachineStatus status;
    private Map<String, Object> configuration = new HashMap<String, Object>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Slot> getSlots() {
        return slots;
    }

    public void setSlots(List<Slot> slots) {
        this.slots = slots;
    }

    public PaymentProcessor getProcessor() {
        return processor;
    }

    public void setProcessor(PaymentProcessor processor) {
        this.processor = processor;
    }

    public VendingMachineStatus getStatus() {
        return status;
    }

    public void setStatus(VendingMachineStatus status) {
        this.status = status;
    }

    public Map<String, Object> getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Map<String, Object> configuration) {
        this.configuration = configuration;
    }
}
