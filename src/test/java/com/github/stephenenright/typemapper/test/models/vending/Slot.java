package com.github.stephenenright.typemapper.test.models.vending;

import java.util.LinkedHashSet;
import java.util.Set;

public class Slot extends BaseModel {

	private String code;
	private Double price;

    private Set<SlotProduct> products = new LinkedHashSet<>();

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

    public Set<SlotProduct> getProducts() {
        return products;
    }

    public void setProducts(Set<SlotProduct> products) {
        this.products = products;
    }
}
