package com.stephenenright.typemapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.stephenenright.typemapper.test.dto.vending.PaymentGatewayDto;
import com.stephenenright.typemapper.test.dto.vending.PaymentProcessorDto;
import com.stephenenright.typemapper.test.dto.vending.ProductCategoryDto;
import com.stephenenright.typemapper.test.dto.vending.ProductDto;
import com.stephenenright.typemapper.test.dto.vending.SlotDto;
import com.stephenenright.typemapper.test.dto.vending.SlotProductDto;
import com.stephenenright.typemapper.test.dto.vending.VendingMachineDto;
import com.stephenenright.typemapper.test.models.vending.PaymentGateway;
import com.stephenenright.typemapper.test.models.vending.PaymentProcessor;
import com.stephenenright.typemapper.test.models.vending.Product;
import com.stephenenright.typemapper.test.models.vending.ProductCategory;
import com.stephenenright.typemapper.test.models.vending.Slot;
import com.stephenenright.typemapper.test.models.vending.SlotProduct;
import com.stephenenright.typemapper.test.models.vending.VendingMachine;

public class TypeMapperTest {

    
    @Test
    public void map() {
        PaymentProcessor processor = new PaymentProcessor();
        processor.setDeleted(false);
        processor.setId("P1");
        processor.setName("PRO Name 1");

        PaymentGateway gateway = new PaymentGateway();
        gateway.setDeleted(false);
        gateway.setId("Gateway 1");
        processor.setGateway(gateway);

        ProductCategory category = new ProductCategory();
        category.setId("1");
        category.setName("Category 1");
        category.setDeleted(false);

        Product product1 = new Product();
        product1.setId("1");
        product1.setCategory(category);
        product1.setName("Product 1");
        product1.setDeleted(false);

        Product product2 = new Product();
        product2.setId("2");
        product2.setCategory(category);
        product2.setName("Product 2");
        product2.setDeleted(false);

        VendingMachine machine = new VendingMachine();
        machine.setId("V1");
        machine.setName("Vending Machine 1");
        machine.setDeleted(true);
        machine.setProcessor(processor);
        
        Map<String,Object> configuration = new HashMap<String, Object>();
        configuration.put("exactAmountRequired", false);
        configuration.put("shutdownWhenEmpty", true);
        machine.setConfiguration(configuration);
        

        List<Slot> slots = new LinkedList<Slot>();

        for (int j = 1; j <= 10; j++) {
            Slot slot = new Slot();
            slot.setDeleted(false);
            slot.setId(String.valueOf(j));
            slot.setCode(String.valueOf(j));
            slot.setPrice(Double.valueOf(String.valueOf(j) + ".00"));
            slots.add(slot);

            Set<SlotProduct> products = new HashSet<>();
            SlotProduct slotProduct = new SlotProduct(product1, slot);
            SlotProduct slotProduct2 = new SlotProduct(product2, slot);
            products.add(slotProduct);
            products.add(slotProduct2);
            slot.setProducts(products);
        }

        machine.setSlots(slots);

        VendingMachineDto result = new TypeMapper().map(machine, VendingMachineDto.class);
        assertNotNull(result);
        assertEquals("V1", result.getId());
        assertEquals("Vending Machine 1", result.getName());
        assertEquals(true, result.isDeleted());
        
        assertTrue(!result.getConfiguration().isEmpty());
        assertTrue((Boolean) result.getConfiguration().get("shutdownWhenEmpty")); 
        assertFalse((Boolean) result.getConfiguration().get("exactAmountRequired")); 
        
   
        PaymentProcessorDto processorDto = result.getProcessor();
        assertEquals("P1", processorDto.getId());
        assertEquals("PRO Name 1", processorDto.getName());
        assertEquals(false, processorDto.isDeleted());

        PaymentGatewayDto gatewayDto = processorDto.getGateway();
        assertNotNull(gatewayDto);
        assertEquals("Gateway 1", gatewayDto.getId());
        assertEquals(false, gatewayDto.isDeleted());

        List<SlotDto> slotsDto = result.getSlots();
        assertNotNull(slotsDto);
        assertTrue(slotsDto.size() == 10);

        for (int i = 0, j = 1; i < 10; i++, j++) {
            SlotDto slotDto = slotsDto.get(i);
            assertEquals(false, slotDto.isDeleted());
            assertEquals(String.valueOf(j), slotDto.getId());
            assertEquals(String.valueOf(j), slotDto.getCode());
            assertEquals(Double.valueOf(String.valueOf(j) + ".00"), slotDto.getPrice());

            Set<SlotProductDto> productDtoSet = slotDto.getProducts();
            assertNotNull(productDtoSet);
            assertEquals(2, productDtoSet.size());

            Set<String> expectedProductIds = new HashSet<String>();
            expectedProductIds.add("1");

            for (SlotProductDto slotProductDto : productDtoSet) {
                if (expectedProductIds.contains(slotProductDto.getId().getProductId())) {
                    expectedProductIds.remove(slotProductDto.getId().getProductId());
                }

                assertNotNull(slotProductDto.getSlot());
                assertEquals(String.valueOf(j), slotProductDto.getSlot().getId());

                ProductDto productDto = slotProductDto.getProduct();
                assertNotNull(productDto);

                if (productDto.getId().equals("1")) {
                    assertEquals("Product 1", productDto.getName());

                } else if (productDto.getId().equals("2")) {
                    assertEquals("Product 2", productDto.getName());
                }

                ProductCategoryDto categoryDto = productDto.getCategory();
                assertEquals("1", categoryDto.getId());
                assertEquals("Category 1", categoryDto.getName());
            }

            assertTrue(expectedProductIds.isEmpty());
        }
    }
}
