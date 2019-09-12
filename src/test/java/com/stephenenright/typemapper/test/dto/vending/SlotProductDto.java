package com.stephenenright.typemapper.test.dto.vending;

import java.io.Serializable;

public class SlotProductDto {

    private SlotProductId id = new SlotProductId();

    private ProductDto product;

    private SlotDto slot;

    public SlotProductDto() {
    }

    public SlotProductDto(ProductDto product, SlotDto slot) {
        this.product = product;
        this.slot = slot;

        this.id.slotId = slot.getId();
        this.id.productId = product.getId();
    }

    public SlotProductId getId() {
        return id;
    }

    public void setId(SlotProductId id) {
        this.id = id;
    }

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public SlotDto getSlot() {
        return slot;
    }

    public void setSlot(SlotDto slot) {
        this.slot = slot;
    }

    public boolean equals(Object o) {
        if (o != null && o instanceof SlotProductDto) {
            SlotProductDto that = (SlotProductDto) o;
            return this.id.equals(that.id);
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.id.hashCode();
    }

    @SuppressWarnings("serial")
    public static class SlotProductId implements Serializable {
        private String slotId;

        private String productId;

        public SlotProductId() {

        }

        public SlotProductId(String productId, String slotId) {
            this.productId = productId;
            this.slotId = slotId;
        }

        public String getSlotId() {
            return slotId;
        }

        public String getProductId() {
            return productId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof SlotProductId) {
                SlotProductId that = (SlotProductId) o;
                return this.productId.equals(that.productId) && this.slotId.equals(that.slotId);
            } else {
                return false;
            }
        }

        public int hashCode() {
            return productId.hashCode() + slotId.hashCode();
        }
    }
}
