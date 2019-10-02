package com.stephenenright.typemapper.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.stephenenright.typemapper.test.dto.vending.PaymentGatewayDto;
import com.stephenenright.typemapper.test.dto.vending.PaymentProcessorDto;
import com.stephenenright.typemapper.test.fixture.utils.FixtureUtils;
import com.stephenenright.typemapper.test.models.vending.PaymentGateway;
import com.stephenenright.typemapper.test.models.vending.PaymentProcessor;

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

}
