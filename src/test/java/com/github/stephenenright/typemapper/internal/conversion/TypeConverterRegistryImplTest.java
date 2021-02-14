package com.github.stephenenright.typemapper.internal.conversion;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.internal.conversion.converter.factory.NumberToNumberTypeConverterFactory;

public class TypeConverterRegistryImplTest {

    private TypeConverterRegistryImpl registry;

    @Before
    public void setup() {
        registry = new TypeConverterRegistryImpl();
        registry.registerConverterFactory(NumberToNumberTypeConverterFactory.INSTANCE);

    }

    @Test
    public void getConverter() {
        TypeConverter<?, ?> converter = registry.getConverter(Integer.class, Long.class);
        assertNotNull(converter);

    }

}
