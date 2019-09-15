package com.stephenenright.typemapper.test.fixture.utils;

import java.util.Arrays;

import com.stephenenright.typemapper.internal.TypeMappingServiceImpl;
import com.stephenenright.typemapper.internal.conversion.TypeConverterCollectionDefaultImpl;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistryImpl;

public abstract class FixtureUtils {

    public static TypeMappingServiceImpl createDefaultMappingService() {
        return createMappingService(createDefaultConverterRegistry());
    }

    public static TypeMappingServiceImpl createMappingService(TypeConverterRegistry... converterRegistries) {
        return new TypeMappingServiceImpl(Arrays.asList(converterRegistries));
    }

    public static TypeConverterRegistryImpl createDefaultConverterRegistry() {
        return new TypeConverterRegistryImpl(new TypeConverterCollectionDefaultImpl());
    }

}
