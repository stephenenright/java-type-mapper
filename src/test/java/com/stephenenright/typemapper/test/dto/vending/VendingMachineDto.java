package com.stephenenright.typemapper.test.dto.vending;

import java.util.LinkedList;
import java.util.List;

public class VendingMachineDto extends BaseDto {

    private String name;
    private List<SlotDto> slots = new LinkedList<SlotDto>();
    private PaymentProcessorDto processor;
    private Integer status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SlotDto> getSlots() {
        return slots;
    }

    public void setSlots(List<SlotDto> slots) {
        this.slots = slots;
    }

    public PaymentProcessorDto getProcessor() {
        return processor;
    }

    public void setProcessor(PaymentProcessorDto processor) {
        this.processor = processor;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
