package com.stephenenright.typemapper.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.stephenenright.typemapper.test.fixture.utils.FixtureUtils;

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
}
