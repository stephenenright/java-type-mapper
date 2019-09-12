package com.stephenenright.typemapper.test.dto.vending;

import java.util.LinkedHashSet;
import java.util.Set;

public class SlotDto extends BaseDto {

	private String code;
	private Double price;

    private Set<SlotProductDto> products = new LinkedHashSet<>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

    public Set<SlotProductDto> getProducts() {
        return products;
    }

    public void setProducts(Set<SlotProductDto> products) {
        this.products = products;
    }
}
