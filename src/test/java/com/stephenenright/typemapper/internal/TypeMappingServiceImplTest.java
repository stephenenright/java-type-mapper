package com.stephenenright.typemapper.internal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.stephenenright.typemapper.test.dto.vending.VendingMachineDto;
import com.stephenenright.typemapper.test.models.vending.VendingMachine;

public class TypeMappingServiceImplTest {

    private TypeMappingServiceImpl mappingService;

    @Before
    public void setup() {
        mappingService = new TypeMappingServiceImpl();
    }

    @Test
    public void map() {
        VendingMachine source = new VendingMachine();
        VendingMachineDto result = mappingService.map(source, VendingMachineDto.class);

        Assert.assertTrue(result.getClass().isAssignableFrom(VendingMachineDto.class));

    }
}
