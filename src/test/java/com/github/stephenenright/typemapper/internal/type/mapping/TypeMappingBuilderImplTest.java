package com.github.stephenenright.typemapper.internal.type.mapping;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.stephenenright.typemapper.DefaultMapperConfiguration;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;
import com.github.stephenenright.typemapper.TypeMappingService;
import org.junit.Before;
import org.junit.Test;

import com.github.stephenenright.typemapper.internal.TypeMappingContextImpl;
import com.github.stephenenright.typemapper.internal.TypeMappingToStrategy;
import com.github.stephenenright.typemapper.test.dto.vending.PaymentProcessorDto;
import com.github.stephenenright.typemapper.test.dto.vending.VendingMachineDto;
import com.github.stephenenright.typemapper.test.fixture.utils.FixtureUtils;
import com.github.stephenenright.typemapper.test.models.vending.PaymentProcessor;
import com.github.stephenenright.typemapper.test.models.vending.VendingMachine;

public class TypeMappingBuilderImplTest {

    private TypeMappingService mappingService;
    private TypeMappingBuilder builder;
    private TypeMappingInfoRegistry mappingInfoRegistry;

    @Before
    public void setup() {
        builder = new TypeMappingBuilderImpl(FixtureUtils.createTypeInfoRegistry());
        mappingInfoRegistry = FixtureUtils.createTypeMappingInfoRegistry(FixtureUtils.createTypeInfoRegistry());
        mappingService = FixtureUtils.createDefaultMappingService();
    }

    @Test
    public void buildMapping() {
        Map<String, String> expectedMappings = new HashMap<String, String>();
        expectedMappings.put("id", "id");
        expectedMappings.put("name", "name");
        expectedMappings.put("deleted", "deleted");
        expectedMappings.put("gateway.deleted", "gateway.deleted");
        expectedMappings.put("gateway.name", "gateway.name");
        expectedMappings.put("gateway.id", "gateway.id");

        TypeMapperConfiguration configuration = DefaultMapperConfiguration.create();
        PaymentProcessor sourceObject = new PaymentProcessor();
        TypeMappingContextImpl<PaymentProcessor, PaymentProcessorDto> contextImpl = 
                new TypeMappingContextImpl<>(configuration, sourceObject, PaymentProcessorDto.class, mappingService, TypeMappingToStrategy.OBJECT);
        
        TypeMappingInfo<PaymentProcessor, PaymentProcessorDto> mappingInfo = FixtureUtils
                .createDefaultTypeMappingInfo(PaymentProcessor.class, PaymentProcessorDto.class, configuration);
        builder.buildMappings(sourceObject, mappingInfo, contextImpl, mappingInfoRegistry);

        List<TypeMapping> mappingsList = mappingInfo.getTypeMappings();

        assertNotNull(mappingsList);
        assertTrue(mappingsList.size() > 0);

        for (TypeMapping mapping : mappingsList) {
            String destinationPath = expectedMappings.get(mapping.getSourcePath());

            if (destinationPath.equals(mapping.getDestinationPath())) {
                expectedMappings.remove(mapping.getSourcePath());
            }
        }

        assertTrue("Not all expected mappings matched", expectedMappings.isEmpty());
        
        
        expectedMappings = new HashMap<String, String>();
        expectedMappings.put("id", "id");
        expectedMappings.put("slots", "slots");
        expectedMappings.put("deleted", "deleted");
        expectedMappings.put("name", "name");
        expectedMappings.put("configuration", "configuration");
        expectedMappings.put("status", "status");
        expectedMappings.put("processor.gateway", "processor.gateway");
        expectedMappings.put("processor.deleted", "processor.deleted");
        expectedMappings.put("processor.name", "processor.name");
        expectedMappings.put("processor.id", "processor.id");
        expectedMappings.put("processor.gateway", "processor.gateway");
        expectedMappings.put("processor.gateway.deleted", "processor.gateway.deleted");
        expectedMappings.put("processor.gateway.name", "processor.gateway.name");
        expectedMappings.put("processor.gateway.id", "processor.gateway.id");
        
        VendingMachine vendingMachine = new VendingMachine();
        TypeMappingContextImpl<VendingMachine,VendingMachineDto> contextImpl2 = 
                new TypeMappingContextImpl<>(configuration, vendingMachine, VendingMachineDto.class, mappingService, TypeMappingToStrategy.OBJECT);
       
        TypeMappingInfo<VendingMachine, VendingMachineDto> mappingInfo2 = FixtureUtils
                .createDefaultTypeMappingInfo(VendingMachine.class, VendingMachineDto.class, DefaultMapperConfiguration.create());
       
        builder.buildMappings(vendingMachine, mappingInfo2, contextImpl2, mappingInfoRegistry);

        mappingsList = mappingInfo2.getTypeMappings();
        
        assertNotNull(mappingsList);
        
        assertTrue(mappingsList.size() > 0);

        for (TypeMapping mapping : mappingsList) {
            String destinationPath = expectedMappings.get(mapping.getSourcePath());

            
            if (destinationPath.equals(mapping.getDestinationPath())) {
                expectedMappings.remove(mapping.getSourcePath());
            }
        }
    }
}
