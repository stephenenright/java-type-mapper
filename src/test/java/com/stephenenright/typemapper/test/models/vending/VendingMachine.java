package com.stephenenright.typemapper.test.models.vending;

import java.util.LinkedList;
import java.util.List;

public class VendingMachine extends BaseModel {

    private String name;
    private List<Slot> slots = new LinkedList<Slot>();
    private PaymentProcessor processor;
    private VendingMachineStatus status;

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

}
