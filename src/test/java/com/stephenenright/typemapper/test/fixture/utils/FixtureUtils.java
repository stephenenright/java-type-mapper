package com.stephenenright.typemapper.test.fixture.utils;

import java.util.Arrays;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.TypeMappingServiceImpl;
import com.stephenenright.typemapper.internal.conversion.TypeConverterCollectionDefaultImpl;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistryImpl;
import com.stephenenright.typemapper.internal.type.info.TypeInfoCreatorDefaultImpl;
import com.stephenenright.typemapper.internal.type.info.TypeInfoRegistryImpl;
import com.stephenenright.typemapper.internal.type.info.TypeIntrospectorImpl;
import com.stephenenright.typemapper.internal.type.info.TypePropertyInfoCollectorImpl;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfo;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfoImpl;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfoRegistryImpl;

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

    public static TypeInfoRegistryImpl createTypeInfoRegistry() {
        return new TypeInfoRegistryImpl(
                new TypeInfoCreatorDefaultImpl(new TypePropertyInfoCollectorImpl(new TypeIntrospectorImpl())));
    }

    public static TypeMappingInfoRegistryImpl createTypeMappingInfoRegistry() {
        return new TypeMappingInfoRegistryImpl();
    }

    public static <S, D> TypeMappingInfo<S, D> createDefaultTypeMappingInfo(Class<S> sourceType, Class<D> destinationType) {
        TypeMapperConfiguration configuration = TypeMapperConfiguration.create();
        TypeMappingInfoImpl<S, D> mappingInfo = new TypeMappingInfoImpl<S, D>(sourceType, destinationType,
                configuration);

        return mappingInfo;

    }

}
