package com.github.stephenenright.typemapper.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.stephenenright.typemapper.DefaultMapperConfiguration;
import com.github.stephenenright.typemapper.MapMapperConfiguration;
import com.github.stephenenright.typemapper.test.fixture.utils.FixtureUtils;
import org.junit.Before;
import org.junit.Test;

import com.github.stephenenright.typemapper.internal.common.CommonConstants;
import com.github.stephenenright.typemapper.test.dto.vending.PaymentGatewayDto;
import com.github.stephenenright.typemapper.test.dto.vending.PaymentProcessorDto;
import com.github.stephenenright.typemapper.test.dto.vending.ProductCategoryDto;
import com.github.stephenenright.typemapper.test.dto.vending.ProductDto;
import com.github.stephenenright.typemapper.test.dto.vending.SlotDto;
import com.github.stephenenright.typemapper.test.dto.vending.SlotProductDto;
import com.github.stephenenright.typemapper.test.dto.vending.VendingMachineDto;
import com.github.stephenenright.typemapper.test.models.vending.PaymentGateway;
import com.github.stephenenright.typemapper.test.models.vending.PaymentProcessor;
import com.github.stephenenright.typemapper.test.models.vending.Product;
import com.github.stephenenright.typemapper.test.models.vending.ProductCategory;
import com.github.stephenenright.typemapper.test.models.vending.Slot;
import com.github.stephenenright.typemapper.test.models.vending.SlotProduct;
import com.github.stephenenright.typemapper.test.models.vending.VendingMachine;

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
    public void map_IntegerToDoubleWithPostTransformation() {
        DefaultMapperConfiguration config = DefaultMapperConfiguration.create();
        config.setPostTransformer(( Integer source, Double dest) -> {
            return 2.0;
        });
        
        Double result = mappingService.map(100, Double.class, config);
        assertNotNull(result);
        assertEquals(result, Double.valueOf(2));
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
    public void map_fromMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("id", "V1");
        map.put("name", "Vending Machine 1");
        map.put("deleted", true);
  
        Map<String,Object> processor = new HashMap<String, Object>();
        processor.put("id", "P1");
        processor.put("name", "PRO Name 1");
        processor.put("deleted", false);
        
        Map<String,Object> gateway = new HashMap<String, Object>();
        gateway.put("id", "Gateway 1");
        gateway.put("deleted", true);
        processor.put("gateway", gateway);

        map.put("processor", processor);
        
        Map<String, Object> configurationMap = new HashMap<String, Object>();
        configurationMap.put("exactAmountRequired", false);
        configurationMap.put("shutdownWhenEmpty", true);
        List<Object> buttons = new LinkedList<Object>();
        List<Object> buttonsWarnings = new LinkedList<Object>();
        Map<String, Object> warningButton = new HashMap<>();
        warningButton.put("name", "warnButton1");
        buttonsWarnings.add(warningButton);
        buttons.add(buttonsWarnings);
        buttons.add("on button");
        configurationMap.put("buttons", buttons);
        
        map.put("configuration", configurationMap);
        
        
        Map<String,Object> categoryMap = new HashMap<String, Object>();
        categoryMap.put("id","1");
        categoryMap.put("name","Category 1");
        categoryMap.put("deleted",false);
        
        Map<String,Object> productMap1 = new HashMap<>();
        productMap1.put("id", "1");
        productMap1.put("category",categoryMap);
        productMap1.put("name","Product 1");
        productMap1.put("deleted",false);
        
        
        Map<String,Object> productMap2 = new HashMap<>();
        productMap2.put("id", "2");
        productMap2.put("category",categoryMap);
        productMap2.put("name","Product 2");
        productMap2.put("deleted",false);
        
 
        List<Map<String,Object>> slots = new LinkedList<>();

        for (int j = 1; j <= 10; j++) {
            Map<String,Object> slot = new HashMap<>();
            slot.put("deleted",false);
            slot.put("id",String.valueOf(j));
            slot.put("code",String.valueOf(j));
            slot.put("price",Double.valueOf(String.valueOf(j) + ".00"));
            slots.add(slot);

            Set<Map<String,Object>> slotProducts = new HashSet<>();
            
            Map<String,Object> slotProduct1 = new HashMap<>();
            slotProduct1.put("slot", slot);
            slotProduct1.put("product", productMap1);
            Map<String,Object> id1 = new HashMap<>();
            id1.put("productId", "1");
            id1.put("slotId", String.valueOf(j));
            slotProduct1.put("id", id1);
            
            Map<String,Object> slotProduct2 = new HashMap<>();
            slotProduct2.put("slot", slot);
            slotProduct2.put("product", productMap2);
            Map<String,Object> id2 = new HashMap<>();
            id2.put("productId", "2");
            id2.put("slotId", String.valueOf(j));
            slotProduct2.put("id", id2);
            
                slotProducts.add(slotProduct1);
            slotProducts.add(slotProduct2);
            slot.put("products", slotProducts);
       
        }

        map.put("slots", slots);
        
    
        VendingMachineDto result = mappingService.map(map, VendingMachineDto.class);
        assertNotNull(result);
        assertEquals("V1", result.getId());
        assertEquals("Vending Machine 1", result.getName());
        assertTrue("Vending Machine 1", result.isDeleted());
        
        
        PaymentProcessorDto processorDto = result.getProcessor();
        assertEquals("P1", processorDto.getId());
        assertEquals("PRO Name 1", processorDto.getName());
        assertEquals(false, processorDto.isDeleted());

        PaymentGatewayDto gatewayDto = processorDto.getGateway();
        assertNotNull(gatewayDto);
        assertEquals("Gateway 1", gatewayDto.getId());
        assertEquals(true, gatewayDto.isDeleted());
        
        Map<String, Object> configuration = result.getConfiguration();
        assertTrue((Boolean) configuration.get("shutdownWhenEmpty"));
        assertFalse((Boolean) configuration.get("exactAmountRequired"));

        List<Object> resultButtons = (List<Object>) configuration.get("buttons");
        assertTrue(resultButtons.size() == 2);

        List<Object> resultWarningButtons = (List<Object>) resultButtons.get(0);
        assertTrue(resultWarningButtons.size() == 1);
        Map<String, Object> warningButton1 = (Map<String, Object>) resultWarningButtons.get(0);
        assertEquals("warnButton1", warningButton1.get("name"));

        String onButton = (String) resultButtons.get(1);
        assertEquals("on button", onButton);
        
        

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
    
    

    @Test
    public void map() {
        VendingMachine machine = createVendingMachine("V1", "Vending Machine 1");

        VendingMachineDto result = mappingService.map(machine, VendingMachineDto.class);
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
    
    
    @Test
    public void map_includesExcludes() {
        VendingMachine machine = createVendingMachine("V1", "Vending Machine 1");

        
        DefaultMapperConfiguration mapperConfig = DefaultMapperConfiguration.create();
        mapperConfig.addIncludeMapping("*", "configuration.**", "processor.*", "slots.**" );
        mapperConfig.addExcludeMapping("processor");
        
        
        VendingMachineDto result = mappingService.map(machine, VendingMachineDto.class, mapperConfig);
        assertNotNull(result);
        assertEquals("V1", result.getId());
        assertEquals("Vending Machine 1", result.getName());
        assertEquals(true, result.isDeleted());

        assertTrue(!result.getConfiguration().isEmpty());
        assertTrue((Boolean) result.getConfiguration().get("shutdownWhenEmpty"));
        assertFalse((Boolean) result.getConfiguration().get("exactAmountRequired"));

        PaymentProcessorDto processorDto = result.getProcessor();
        assertNull(processorDto);
        
 
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
    
    @Test
    public void map_withPropertyTransforms() {
        VendingMachine machine = createVendingMachine("V1", "Vending Machine 1");

        
        DefaultMapperConfiguration mapperConfig = DefaultMapperConfiguration.create();
        mapperConfig.addPropertyTransformer("name", (src, dest, currentSrc, currentDest) -> {
            return "Vending Machine Transformed";
        });
        mapperConfig.addPropertyTransformer("slots", (src, dest, currentSrc, currentDest) -> {
            return new LinkedList<>();
        });
     
        VendingMachineDto result = mappingService.map(machine, VendingMachineDto.class, mapperConfig);
        assertNotNull(result);
        assertEquals("V1", result.getId());
        assertEquals("Vending Machine Transformed", result.getName());
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
        assertTrue(slotsDto.size() == 0);

    }
    
    @Test
    public void mapToMap_String() {
        String expectedValue = "This is a String";
        Map<String, Object> result = mappingService.mapToMap(expectedValue);
        assertTrue(result.containsKey(CommonConstants.PROPERTY_NAME_VALUE));
        assertEquals(result.get(CommonConstants.PROPERTY_NAME_VALUE), expectedValue);
    }

    @Test
    public void mapToMap_Primitive() {
        Integer expectedValue = 2;
        Map<String, Object> result = mappingService.mapToMap(expectedValue);
        assertTrue(result.containsKey(CommonConstants.PROPERTY_NAME_VALUE));
        assertEquals(result.get(CommonConstants.PROPERTY_NAME_VALUE), expectedValue);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void mapToMap_Bean() {
        VendingMachine machine = createVendingMachine("V1", "Vending Machine 1");

        List<Object> buttons = new LinkedList<Object>();
        List<Object> buttonsWarnings = new LinkedList<Object>();
        Map<String, Object> warningButton = new HashMap<>();
        warningButton.put("name", "warnButton1");
        buttonsWarnings.add(warningButton);
        buttons.add(buttonsWarnings);
        buttons.add("on button");

        machine.getConfiguration().put("buttons", buttons);

        Map<String, Object> result = mappingService.mapToMap(machine);
        assertFalse(result.isEmpty());

        assertEquals("V1", result.get("id"));
        assertEquals("Vending Machine 1", result.get("name"));
        assertEquals(true, result.get("deleted"));

        Map<String, Object> configuration = (Map<String, Object>) result.get("configuration");
        assertTrue((Boolean) configuration.get("shutdownWhenEmpty"));
        assertFalse((Boolean) configuration.get("exactAmountRequired"));

        List<Object> resultButtons = (List<Object>) configuration.get("buttons");
        assertTrue(resultButtons.size() == 2);

        List<Object> resultWarningButtons = (List<Object>) resultButtons.get(0);
        assertTrue(resultWarningButtons.size() == 1);
        Map<String, Object> warningButton1 = (Map<String, Object>) resultWarningButtons.get(0);
        assertEquals("warnButton1", warningButton1.get("name"));

        String onButton = (String) resultButtons.get(1);
        assertEquals("on button", onButton);

        Map<String, Object> processor = (Map<String, Object>) result.get("processor");
        assertEquals("P1", processor.get("id"));
        assertEquals("PRO Name 1", processor.get("name"));
        assertEquals(false, processor.get("deleted"));

        Map<String, Object> gateway = (Map<String, Object>) processor.get("gateway");
        assertNotNull(gateway);
        assertEquals("Gateway 1", gateway.get("id"));
        assertEquals(false, gateway.get("deleted"));

        List<Object> slots = (List<Object>) result.get("slots");
        assertNotNull(slots);
        assertTrue(slots.size() == 10);

        for (int i = 0, j = 1; i < 10; i++, j++) {
            Map<String, Object> slot = (Map<String, Object>) slots.get(i);
            assertEquals(false, slot.get("deleted"));
            assertEquals(String.valueOf(j), slot.get("id"));
            assertEquals(String.valueOf(j), slot.get("code"));
            assertEquals(Double.valueOf(String.valueOf(j) + ".00"), slot.get("price"));

            List<Map<String, Object>> products = (List<Map<String, Object>>) slot.get("products");
            assertNotNull(products);
            assertEquals(2, products.size());

            Set<String> expectedProductIds = new HashSet<String>();
            expectedProductIds.add("1");

            for (Map<String, Object> slotProduct : products) {
                Map<String, Object> slotProductId = (Map<String, Object>) slotProduct.get("id");
                assertEquals(slot.get("id"), slotProductId.get("slotId"));

                if (expectedProductIds.contains(slotProductId.get("productId"))) {
                    expectedProductIds.remove(slotProductId.get("productId"));
                }

                Map<String, Object> slotFromRel = (Map<String, Object>) slotProduct.get("slot");

                assertNotNull(slotFromRel);
                assertEquals(String.valueOf(j), slotFromRel.get("id"));

                Map<String, Object> productFromRel = (Map<String, Object>) slotProduct.get("product");
                assertNotNull(productFromRel);

                if (productFromRel.get("id").equals("1")) {
                    assertEquals("Product 1", productFromRel.get("name"));

                } else if (productFromRel.get("id").equals("2")) {
                    assertEquals("Product 2", productFromRel.get("name"));
                }

                Map<String, Object> category = (Map<String, Object>) productFromRel.get("category");
                assertEquals("1", category.get("id"));
                assertEquals("Category 1", category.get("name"));

            }

            assertTrue(expectedProductIds.isEmpty());
        }
    }
    
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void mapToMap_BeanWithIncludesExcludesAndTransformations() {
        VendingMachine machine = createVendingMachine("V1", "Vending Machine 1");

        List<Object> buttons = new LinkedList<Object>();
        List<Object> buttonsWarnings = new LinkedList<Object>();
        Map<String, Object> warningButton = new HashMap<>();
        warningButton.put("name", "warnButton1");
        buttonsWarnings.add(warningButton);
        buttons.add(buttonsWarnings);
        buttons.add("on button");

        machine.getConfiguration().put("buttons", buttons);

        MapMapperConfiguration mapperConfig = MapMapperConfiguration.create();
        mapperConfig.addIncludeMapping("*", "configuration.**", "processor.*", "slots.**" );
        mapperConfig.addExcludeMapping("processor");
        mapperConfig.addPropertyTransformer("name", (src,dest, currentSrc,currentDest) -> {
            return "Name Transformed";
        });
       
        mapperConfig.setPostTransformer((VendingMachine source, Map<String,Object> dest) -> {
            dest.put("addedValue", "addedValue");
            return dest;
        });
        
    
        Map<String, Object> result = mappingService.mapToMap(machine,mapperConfig);
        assertFalse(result.isEmpty());

        assertEquals("V1", result.get("id"));
        assertEquals("Name Transformed", result.get("name"));
        assertEquals(true, result.get("deleted"));
        assertEquals("addedValue", result.get("addedValue"));
        
        

        Map<String, Object> configuration = (Map<String, Object>) result.get("configuration");
        assertTrue((Boolean) configuration.get("shutdownWhenEmpty"));
        assertFalse((Boolean) configuration.get("exactAmountRequired"));

        List<Object> resultButtons = (List<Object>) configuration.get("buttons");
        assertTrue(resultButtons.size() == 2);

        List<Object> resultWarningButtons = (List<Object>) resultButtons.get(0);
        assertTrue(resultWarningButtons.size() == 1);
        Map<String, Object> warningButton1 = (Map<String, Object>) resultWarningButtons.get(0);
        assertEquals("warnButton1", warningButton1.get("name"));

        String onButton = (String) resultButtons.get(1);
        assertEquals("on button", onButton);

        assertFalse(result.containsKey("processor"));
  
        List<Object> slots = (List<Object>) result.get("slots");
        assertNotNull(slots);
        assertTrue(slots.size() == 10);

        for (int i = 0, j = 1; i < 10; i++, j++) {
            Map<String, Object> slot = (Map<String, Object>) slots.get(i);
            assertEquals(false, slot.get("deleted"));
            assertEquals(String.valueOf(j), slot.get("id"));
            assertEquals(String.valueOf(j), slot.get("code"));
            assertEquals(Double.valueOf(String.valueOf(j) + ".00"), slot.get("price"));

            List<Map<String, Object>> products = (List<Map<String, Object>>) slot.get("products");
            assertNotNull(products);
            assertEquals(2, products.size());

            Set<String> expectedProductIds = new HashSet<String>();
            expectedProductIds.add("1");

            for (Map<String, Object> slotProduct : products) {
                Map<String, Object> slotProductId = (Map<String, Object>) slotProduct.get("id");
                assertEquals(slot.get("id"), slotProductId.get("slotId"));

                if (expectedProductIds.contains(slotProductId.get("productId"))) {
                    expectedProductIds.remove(slotProductId.get("productId"));
                }

                Map<String, Object> slotFromRel = (Map<String, Object>) slotProduct.get("slot");

                assertNotNull(slotFromRel);
                assertEquals(String.valueOf(j), slotFromRel.get("id"));

                Map<String, Object> productFromRel = (Map<String, Object>) slotProduct.get("product");
                assertNotNull(productFromRel);

                if (productFromRel.get("id").equals("1")) {
                    assertEquals("Product 1", productFromRel.get("name"));

                } else if (productFromRel.get("id").equals("2")) {
                    assertEquals("Product 2", productFromRel.get("name"));
                }

                Map<String, Object> category = (Map<String, Object>) productFromRel.get("category");
                assertEquals("1", category.get("id"));
                assertEquals("Category 1", category.get("name"));

            }

            assertTrue(expectedProductIds.isEmpty());
        }
        
        
        
        
        
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void mapToListOfMap_Bean() {

        List<VendingMachine> machineList = new ArrayList<VendingMachine>();

        for (int i = 0; i < 10; i++) {
            VendingMachine machine = createVendingMachine("V" + String.valueOf(i), "Vending Machine " + String.valueOf(i));
            List<Object> buttons = new LinkedList<Object>();
            List<Object> buttonsWarnings = new LinkedList<Object>();
            Map<String, Object> warningButton = new HashMap<>();
            warningButton.put("name", "warnButton1");
            buttonsWarnings.add(warningButton);
            buttons.add(buttonsWarnings);
            buttons.add("on button");
            machine.getConfiguration().put("buttons", buttons);
            machineList.add(machine);
        }

        List<Map<String, Object>> resultList = mappingService.mapToListOfMap(machineList);
        assertTrue(resultList.size() == 10);
        
        for(int z=0; z<10; z++) {
            Map<String, Object> result = resultList.get(z);
            
            assertEquals("V" + String.valueOf(z), result.get("id"));
            assertEquals("Vending Machine " + String.valueOf(z), result.get("name"));
            assertEquals(true, result.get("deleted"));

            Map<String, Object> configuration = (Map<String, Object>) result.get("configuration");
            assertTrue((Boolean) configuration.get("shutdownWhenEmpty"));
            assertFalse((Boolean) configuration.get("exactAmountRequired"));

            List<Object> resultButtons = (List<Object>) configuration.get("buttons");
            assertTrue(resultButtons.size() == 2);

            List<Object> resultWarningButtons = (List<Object>) resultButtons.get(0);
            assertTrue(resultWarningButtons.size() == 1);
            Map<String, Object> warningButton1 = (Map<String, Object>) resultWarningButtons.get(0);
            assertEquals("warnButton1", warningButton1.get("name"));

            String onButton = (String) resultButtons.get(1);
            assertEquals("on button", onButton);

            Map<String, Object> processor = (Map<String, Object>) result.get("processor");
            assertEquals("P1", processor.get("id"));
            assertEquals("PRO Name 1", processor.get("name"));
            assertEquals(false, processor.get("deleted"));

            Map<String, Object> gateway = (Map<String, Object>) processor.get("gateway");
            assertNotNull(gateway);
            assertEquals("Gateway 1", gateway.get("id"));
            assertEquals(false, gateway.get("deleted"));

            List<Object> slots = (List<Object>) result.get("slots");
            assertNotNull(slots);
            assertTrue(slots.size() == 10);

            for (int i = 0, j = 1; i < 10; i++, j++) {
                Map<String, Object> slot = (Map<String, Object>) slots.get(i);
                assertEquals(false, slot.get("deleted"));
                assertEquals(String.valueOf(j), slot.get("id"));
                assertEquals(String.valueOf(j), slot.get("code"));
                assertEquals(Double.valueOf(String.valueOf(j) + ".00"), slot.get("price"));

                List<Map<String, Object>> products = (List<Map<String, Object>>) slot.get("products");
                assertNotNull(products);
                assertEquals(2, products.size());

                Set<String> expectedProductIds = new HashSet<String>();
                expectedProductIds.add("1");

                for (Map<String, Object> slotProduct : products) {
                    Map<String, Object> slotProductId = (Map<String, Object>) slotProduct.get("id");
                    assertEquals(slot.get("id"), slotProductId.get("slotId"));

                    if (expectedProductIds.contains(slotProductId.get("productId"))) {
                        expectedProductIds.remove(slotProductId.get("productId"));
                    }

                    Map<String, Object> slotFromRel = (Map<String, Object>) slotProduct.get("slot");

                    assertNotNull(slotFromRel);
                    assertEquals(String.valueOf(j), slotFromRel.get("id"));

                    Map<String, Object> productFromRel = (Map<String, Object>) slotProduct.get("product");
                    assertNotNull(productFromRel);

                    if (productFromRel.get("id").equals("1")) {
                        assertEquals("Product 1", productFromRel.get("name"));

                    } else if (productFromRel.get("id").equals("2")) {
                        assertEquals("Product 2", productFromRel.get("name"));
                    }

                    Map<String, Object> category = (Map<String, Object>) productFromRel.get("category");
                    assertEquals("1", category.get("id"));
                    assertEquals("Category 1", category.get("name"));

                }

                assertTrue(expectedProductIds.isEmpty());
            }
        }
    }

    private VendingMachine createVendingMachine(String id, String name) {
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
        machine.setId(id);
        machine.setName(name);
        machine.setDeleted(true);
        machine.setProcessor(processor);

        Map<String, Object> configuration = new HashMap<String, Object>();
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

        return machine;
    }

}
