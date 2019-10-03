package com.stephenenright.typemapper.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.stephenenright.typemapper.test.dto.vending.PaymentGatewayDto;
import com.stephenenright.typemapper.test.dto.vending.PaymentProcessorDto;
import com.stephenenright.typemapper.test.dto.vending.SlotDto;
import com.stephenenright.typemapper.test.dto.vending.VendingMachineDto;
import com.stephenenright.typemapper.test.fixture.utils.FixtureUtils;
import com.stephenenright.typemapper.test.models.vending.PaymentGateway;
import com.stephenenright.typemapper.test.models.vending.PaymentProcessor;
import com.stephenenright.typemapper.test.models.vending.Product;
import com.stephenenright.typemapper.test.models.vending.ProductCategory;
import com.stephenenright.typemapper.test.models.vending.Slot;
import com.stephenenright.typemapper.test.models.vending.SlotProduct;
import com.stephenenright.typemapper.test.models.vending.VendingMachine;

public class TypeMappingServiceImplTest {

    private TypeMappingServiceImpl mappingService;

    @Before
    public void setup() {
        mappingService = FixtureUtils.createDefaultMappingService();
    }

    @Test
    public void map_IntegerToDouble() {
        Double result = mappingService.map(Integer.valueOf(100), Double.class);
        assertNotNull(result);
        assertEquals(result, Double.valueOf(100));
    }

    @Test
    public void map_sameBean() {
        PaymentProcessor sourceObject = new PaymentProcessor();
        sourceObject.setDeleted(false);
        sourceObject.setId("1");
        sourceObject.setName("Name 1");

        PaymentGateway gateway = new PaymentGateway();
        gateway.setDeleted(false);
        gateway.setId("Gateway 1");
        sourceObject.setGateway(gateway);

        PaymentProcessor result = mappingService.map(sourceObject, PaymentProcessor.class);
        assertNotNull(result);

        assertEquals(result.getId(), "1");
        assertEquals(result.getName(), "Name 1");
        assertEquals(result.isDeleted(), false);

        PaymentGateway resultGateway = result.getGateway();
        assertEquals(resultGateway.getId(), "Gateway 1");

    }

    @Test
    public void map_simpleBean() {
        PaymentProcessor sourceObject = new PaymentProcessor();
        sourceObject.setDeleted(false);
        sourceObject.setId("1");
        sourceObject.setName("Name 1");

        PaymentGateway gateway = new PaymentGateway();
        gateway.setDeleted(false);
        gateway.setId("Gateway 1");
        sourceObject.setGateway(gateway);

        PaymentProcessorDto result = mappingService.map(sourceObject, PaymentProcessorDto.class);
        assertNotNull(result);

        assertEquals(result.getId(), "1");
        assertEquals(result.getName(), "Name 1");
        assertEquals(result.isDeleted(), false);

        PaymentGatewayDto resultGateway = result.getGateway();
        assertEquals(resultGateway.getId(), "Gateway 1");
    }
    
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
        
        
  
        VendingMachine machine = new VendingMachine();
        machine.setId("V1");
        machine.setName("Vending Machine 1");
        machine.setDeleted(true);
        machine.setProcessor(processor);
        
        List<Slot> slots = new LinkedList<Slot>();
        
        for(int j = 1; j<=10; j++) {
            Slot slot = new Slot();
            slot.setDeleted(false); 
            slot.setId(String.valueOf(j));
            slot.setCode(String.valueOf(j));
            slot.setPrice(Double.valueOf(String.valueOf(j) + ".00"));
            slots.add(slot);
            
            Set<SlotProduct> products = new HashSet<>();
            SlotProduct slotProduct = new SlotProduct(product1, slot);
            products.add(slotProduct);
            slot.setProducts(products);
        }
        
        machine.setSlots(slots);
        
        
        
        
        VendingMachineDto result = mappingService.map(machine, VendingMachineDto.class);
        assertNotNull(result);
        assertEquals("V1", result.getId());
        assertEquals("Vending Machine 1", result.getName());
        assertEquals(true, result.isDeleted());
        
        
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
        
        
        for(int i = 0, j=1; i<10; i++, j++) {
            SlotDto slotDto = slotsDto.get(i);
            assertEquals(false, slotDto.isDeleted());
            assertEquals(String.valueOf(j), slotDto.getId());
            assertEquals(String.valueOf(j), slotDto.getCode());
            assertEquals(Double.valueOf(String.valueOf(j) + ".00"), slotDto.getPrice());
        }
        
    }
    
}
