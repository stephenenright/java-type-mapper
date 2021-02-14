package com.github.stephenenright.typemapper.internal.conversion.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.github.stephenenright.typemapper.DefaultMapperConfiguration;
import com.github.stephenenright.typemapper.TypeMappingContext;
import org.junit.Test;

import com.github.stephenenright.typemapper.internal.util.TypeUtils;
import com.github.stephenenright.typemapper.test.fixture.utils.FixtureUtils;
import com.github.stephenenright.typemapper.test.models.vending.VendingMachineStatus;

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
