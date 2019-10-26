package com.stephenenright.typemapper.internal.type.mapping;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.stephenenright.typemapper.DefaultMapperConfiguration;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.TypeMappingService;
import com.stephenenright.typemapper.internal.TypeMappingContextImpl;
import com.stephenenright.typemapper.internal.TypeMappingToStrategy;
import com.stephenenright.typemapper.test.dto.vending.PaymentProcessorDto;
import com.stephenenright.typemapper.test.dto.vending.VendingMachineDto;
import com.stephenenright.typemapper.test.fixture.utils.FixtureUtils;
import com.stephenenright.typemapper.test.models.vending.PaymentProcessor;
import com.stephenenright.typemapper.test.models.vending.VendingMachine;

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
        expectedMappings.put("PaymentProcessor.id", "PaymentProcessorDto.id");
        expectedMappings.put("PaymentProcessor.name", "PaymentProcessorDto.name");
        expectedMappings.put("PaymentProcessor.deleted", "PaymentProcessorDto.deleted");
        expectedMappings.put("PaymentProcessor.gateway.deleted", "PaymentProcessorDto.gateway.deleted");
        expectedMappings.put("PaymentProcessor.gateway.name", "PaymentProcessorDto.gateway.name");
        expectedMappings.put("PaymentProcessor.gateway.id", "PaymentProcessorDto.gateway.id");

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
        expectedMappings.put("VendingMachine.id", "VendingMachineDto.id");
        expectedMappings.put("VendingMachine.slots", "VendingMachineDto.slots");
        expectedMappings.put("VendingMachine.deleted", "VendingMachineDto.deleted");
        expectedMappings.put("VendingMachine.name", "VendingMachineDto.name");
        expectedMappings.put("VendingMachine.configuration", "VendingMachineDto.configuration");
        expectedMappings.put("VendingMachine.status", "VendingMachineDto.status");
        expectedMappings.put("VendingMachine.processor.gateway", "VendingMachineDto.processor.gateway");
        expectedMappings.put("VendingMachine.processor.deleted", "VendingMachineDto.processor.deleted");
        expectedMappings.put("VendingMachine.processor.name", "VendingMachineDto.processor.name");
        expectedMappings.put("VendingMachine.processor.id", "VendingMachineDto.processor.id");
        expectedMappings.put("VendingMachine.processor.gateway", "VendingMachineDto.processor.gateway");
        expectedMappings.put("VendingMachine.processor.gateway.deleted", "VendingMachineDto.processor.gateway.deleted");
        expectedMappings.put("VendingMachine.processor.gateway.name", "VendingMachineDto.processor.gateway.name");
        expectedMappings.put("VendingMachine.processor.gateway.id", "VendingMachineDto.processor.gateway.id");
        
        
        
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
