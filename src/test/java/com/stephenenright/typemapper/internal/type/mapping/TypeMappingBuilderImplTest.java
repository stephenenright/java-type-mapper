package com.stephenenright.typemapper.internal.type.mapping;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.stephenenright.typemapper.test.dto.vending.PaymentProcessorDto;
import com.stephenenright.typemapper.test.fixture.utils.FixtureUtils;
import com.stephenenright.typemapper.test.models.vending.PaymentProcessor;

public class TypeMappingBuilderImplTest {

    private TypeMappingBuilder builder;

    @Before
    public void setup() {
        builder = new TypeMappingBuilderImpl(FixtureUtils.createTypeInfoRegistry(),
                FixtureUtils.createTypeMappingInfoRegistry(), FixtureUtils.createDefaultConverterRegistry());
    }

    @Test
    public void buildMapping() {
        Map<String, String> expectedMappings = new HashMap<String, String>();
        expectedMappings.put("PaymentProcessor.id", "PaymentProcessorDto.id");
        expectedMappings.put("PaymentProcessor.name", "PaymentProcessorDto.name");
        expectedMappings.put("PaymentProcessor.deleted", "PaymentProcessorDto.deleted");
        expectedMappings.put("PaymentProcessor.gateway.deleted","PaymentProcessorDto.gateway.deleted");
        expectedMappings.put("PaymentProcessor.gateway.name","PaymentProcessorDto.gateway.name");
        expectedMappings.put("PaymentProcessor.gateway.id","PaymentProcessorDto.gateway.id");
        
        TypeMappingInfo<PaymentProcessor, PaymentProcessorDto> mappingInfo = FixtureUtils
                .createDefaultTypeMappingInfo(PaymentProcessor.class, PaymentProcessorDto.class);
        PaymentProcessor sourceObject = new PaymentProcessor();
        builder.buildMappings(sourceObject, mappingInfo, mappingInfo.getConfiguration());

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
    }

}
