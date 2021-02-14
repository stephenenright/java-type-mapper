package com.github.stephenenright.typemapper.test.dto.vending;

public class ProductDto extends BaseDto {

    private String name;

    private ProductCategoryDto category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategoryDto getCategory() {
        return category;
    }

    public void setCategory(ProductCategoryDto category) {
        this.category = category;
    }
}
