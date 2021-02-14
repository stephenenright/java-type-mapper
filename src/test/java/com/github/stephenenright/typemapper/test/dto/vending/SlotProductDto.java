package com.github.stephenenright.typemapper.test.dto.vending;

import java.io.Serializable;
import java.util.Objects;

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

        public void setSlotId(String slotId) {
            this.slotId = slotId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public boolean equals(Object o) {
            if (o != null && o instanceof SlotProductId) {
                SlotProductId that = (SlotProductId) o;
                return Objects.equals(this.productId, that.productId) && Objects.equals(this.slotId, that.slotId);
            } else {
                return false;
            }
        }

        public int hashCode() {
            int hashCode = 31;
            if (productId != null) {
                hashCode += productId.hashCode() * 31;
            }

            if (slotId != null) {
                hashCode += slotId.hashCode() * 31;
            }

            return hashCode;
        }
    }
}
