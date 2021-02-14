package com.github.stephenenright.typemapper.test.fixture.utils;

import java.util.Arrays;

import com.github.stephenenright.typemapper.TypeInfoRegistry;
import com.github.stephenenright.typemapper.TypeMapperConfiguration;
import com.github.stephenenright.typemapper.internal.TypeMappingConversionStrategyListOfMapImpl;
import com.github.stephenenright.typemapper.internal.TypeMappingConversionStrategyMapImpl;
import com.github.stephenenright.typemapper.internal.TypeMappingConversionStrategyRegistry;
import com.github.stephenenright.typemapper.internal.TypeMappingServiceImpl;
import com.github.stephenenright.typemapper.internal.TypeMappingContextImpl;
import com.github.stephenenright.typemapper.internal.TypeMappingConversionStrategy;
import com.github.stephenenright.typemapper.internal.TypeMappingConversionStrategyObjectImpl;
import com.github.stephenenright.typemapper.internal.TypeMappingToStrategy;
import com.github.stephenenright.typemapper.internal.conversion.TypeConverterCollectionDefaultImpl;
import com.github.stephenenright.typemapper.internal.conversion.TypeConverterRegistry;
import com.github.stephenenright.typemapper.internal.conversion.TypeConverterRegistryImpl;
import com.github.stephenenright.typemapper.internal.type.info.TypeInfoCreator;
import com.github.stephenenright.typemapper.internal.type.info.TypeInfoCreatorDefaultImpl;
import com.github.stephenenright.typemapper.internal.type.info.TypeInfoRegistryImpl;
import com.github.stephenenright.typemapper.internal.type.info.TypeIntrospectorImpl;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertyInfoCollectorImpl;
import com.github.stephenenright.typemapper.internal.type.info.TypePropertyInfoRegistryImpl;
import com.github.stephenenright.typemapper.internal.type.mapping.TypeMappingBuilderImpl;
import com.github.stephenenright.typemapper.internal.type.mapping.TypeMappingInfo;
import com.github.stephenenright.typemapper.internal.type.mapping.TypeMappingInfoImpl;
import com.github.stephenenright.typemapper.internal.type.mapping.TypeMappingInfoRegistryImpl;

public abstract class FixtureUtils {

    public static <S, D> TypeMappingContextImpl<S, D> createMappingContext(S source, Class<D> destinationType,
            TypeMapperConfiguration configuration) {
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
    
    public static TypeInfoRegistryImpl createTypeInfoRegistry(TypeInfoCreator infoCreator) {
        return new TypeInfoRegistryImpl(infoCreator);
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
