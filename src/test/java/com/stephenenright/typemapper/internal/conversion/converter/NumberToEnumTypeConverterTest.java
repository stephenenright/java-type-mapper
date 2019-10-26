package com.stephenenright.typemapper.internal.conversion.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.stephenenright.typemapper.DefaultMapperConfiguration;
import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.internal.util.TypeUtils;
import com.stephenenright.typemapper.test.fixture.utils.FixtureUtils;
import com.stephenenright.typemapper.test.models.vending.VendingMachineStatus;

public class NumberToEnumTypeConverterTest {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Test
    public void convert_double() {

        TypeMappingContext<Number, Enum> context = FixtureUtils.createMappingContext(1.0d,
                (Class<Enum>) TypeUtils.getEnumType(VendingMachineStatus.class), DefaultMapperConfiguration.create());
        Enum<?> enumResult = NumberToEnumTypeConverter.INSTANCE.convert(context);
        assertTrue(enumResult instanceof VendingMachineStatus);
        assertEquals(enumResult, VendingMachineStatus.ONLINE);

    }

}
