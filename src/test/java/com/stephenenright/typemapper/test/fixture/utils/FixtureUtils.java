package com.stephenenright.typemapper.test.fixture.utils;

import java.util.Arrays;

import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.TypeMappingContextImpl;
import com.stephenenright.typemapper.internal.TypeMappingConversionStrategy;
import com.stephenenright.typemapper.internal.TypeMappingConversionStrategyListOfMapImpl;
import com.stephenenright.typemapper.internal.TypeMappingConversionStrategyMapImpl;
import com.stephenenright.typemapper.internal.TypeMappingConversionStrategyObjectImpl;
import com.stephenenright.typemapper.internal.TypeMappingConversionStrategyRegistry;
import com.stephenenright.typemapper.internal.TypeMappingServiceImpl;
import com.stephenenright.typemapper.internal.TypeMappingToStrategy;
import com.stephenenright.typemapper.internal.conversion.TypeConverterCollectionDefaultImpl;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.stephenenright.typemapper.internal.conversion.TypeConverterRegistryImpl;
import com.stephenenright.typemapper.internal.type.info.TypeInfoCreatorDefaultImpl;
import com.stephenenright.typemapper.internal.type.info.TypeInfoRegistryImpl;
import com.stephenenright.typemapper.internal.type.info.TypeIntrospectorImpl;
import com.stephenenright.typemapper.internal.type.info.TypePropertyInfoCollectorImpl;
import com.stephenenright.typemapper.internal.type.info.TypePropertyInfoRegistryImpl;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingBuilderImpl;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfo;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfoImpl;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfoRegistryImpl;

public abstract class FixtureUtils {

    public static <S, D> TypeMappingContextImpl<S, D> createMappingContext(S source, Class<D> destinationType, TypeMapperConfiguration configuration) {
        return new TypeMappingContextImpl<S, D>(configuration, source, destinationType,
                FixtureUtils.createDefaultMappingService(), TypeMappingToStrategy.OBJECT);
    }

    public static <S, D> TypeMappingContextImpl<S, D> createMappingContext(S source, Class<D> destinationType,
            TypeMappingToStrategy toStrategy, TypeMapperConfiguration configuration) {
        return new TypeMappingContextImpl<S, D>(configuration, source, destinationType,
                FixtureUtils.createDefaultMappingService(), toStrategy);
    }

    public static TypeMappingServiceImpl createDefaultMappingService() {
        return createMappingService(createDefaultConverterRegistry());
    }

    public static TypeMappingServiceImpl createMappingService(TypeConverterRegistry... converterRegistries) {
        TypeInfoRegistry typeInfoRegistry = createTypeInfoRegistry();

        return new TypeMappingServiceImpl(Arrays.asList(converterRegistries),
                createDefaultConversionStrategyRegistry(typeInfoRegistry));
    }

    public static TypeConverterRegistryImpl createDefaultConverterRegistry() {
        return new TypeConverterRegistryImpl(new TypeConverterCollectionDefaultImpl());
    }

    public static TypeInfoRegistryImpl createTypeInfoRegistry() {
        return new TypeInfoRegistryImpl(
                new TypeInfoCreatorDefaultImpl(new TypePropertyInfoCollectorImpl(new TypeIntrospectorImpl())));
    }

    public static TypePropertyInfoRegistryImpl createPropertyInfoRegistry(TypeInfoRegistry infoRegistry) {
        return new TypePropertyInfoRegistryImpl(infoRegistry);
    }

    public static TypeMappingInfoRegistryImpl createTypeMappingInfoRegistry(TypeInfoRegistry typeInfoRegistry) {
        return new TypeMappingInfoRegistryImpl(new TypeMappingBuilderImpl(typeInfoRegistry));
    }

    public static <S, D> TypeMappingInfo<S, D> createDefaultTypeMappingInfo(Class<S> sourceType,
            Class<D> destinationType, TypeMapperConfiguration configuration) {
        TypeMappingInfoImpl<S, D> mappingInfo = new TypeMappingInfoImpl<S, D>(sourceType, destinationType,
                configuration);

        return mappingInfo;
    }

    public static TypeMappingConversionStrategyRegistry createDefaultConversionStrategyRegistry(
            TypeInfoRegistry typeInfoRegistry) {
        TypeMappingConversionStrategyRegistry conversionStrategyRegistry = new TypeMappingConversionStrategyRegistry();

        TypeMappingInfoRegistryImpl mappingInfoRegistry = createTypeMappingInfoRegistry(typeInfoRegistry);
        TypePropertyInfoRegistryImpl typePropertyInfoRegistry = createPropertyInfoRegistry(typeInfoRegistry);

        TypeMappingConversionStrategy objectStrategy = new TypeMappingConversionStrategyObjectImpl(mappingInfoRegistry,
                typeInfoRegistry, typePropertyInfoRegistry);

        TypeMappingConversionStrategy mapStrategy = new TypeMappingConversionStrategyMapImpl(typeInfoRegistry);

        TypeMappingConversionStrategy listOfMapStrategy = new TypeMappingConversionStrategyListOfMapImpl(
                typeInfoRegistry);

        conversionStrategyRegistry.register(TypeMappingToStrategy.OBJECT, objectStrategy);
        conversionStrategyRegistry.register(TypeMappingToStrategy.MAP, mapStrategy);
        conversionStrategyRegistry.register(TypeMappingToStrategy.LIST_OF_MAP, listOfMapStrategy);

        return conversionStrategyRegistry;

    }

}
