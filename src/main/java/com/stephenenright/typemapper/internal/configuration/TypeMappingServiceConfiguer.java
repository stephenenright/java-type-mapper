package com.stephenenright.typemapper.internal.configuration;

import java.util.LinkedList;
import java.util.List;

import com.stephenenright.typemapper.TypeInfoRegistry;
import com.stephenenright.typemapper.TypeMappingConfiguration;
import com.stephenenright.typemapper.TypeMappingService;
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
import com.stephenenright.typemapper.internal.type.info.TypeInfoCreator;
import com.stephenenright.typemapper.internal.type.info.TypeInfoCreatorDefaultImpl;
import com.stephenenright.typemapper.internal.type.info.TypeInfoRegistryImpl;
import com.stephenenright.typemapper.internal.type.info.TypePropertyInfoCollector;
import com.stephenenright.typemapper.internal.type.info.TypePropertyInfoCollectorImpl;
import com.stephenenright.typemapper.internal.type.info.TypePropertyInfoRegistry;
import com.stephenenright.typemapper.internal.type.info.TypePropertyInfoRegistryImpl;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingBuilder;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingBuilderImpl;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfoRegistry;
import com.stephenenright.typemapper.internal.type.mapping.TypeMappingInfoRegistryImpl;

public abstract class TypeMappingServiceConfiguer {

    private TypeMappingServiceConfiguer() {
        
    }
    
    public static TypeMappingService configure(TypeMappingConfiguration configuration) {

        TypeMappingConfigurationImpl configurationImpl = (TypeMappingConfigurationImpl) configuration;

        TypePropertyInfoCollector typePropertyInfoCollector = new TypePropertyInfoCollectorImpl(
                configuration.getIntrospector());

        TypeInfoCreator typeInfoCreator = new TypeInfoCreatorDefaultImpl(typePropertyInfoCollector);

        TypeInfoRegistry typeInfoRegistry = new TypeInfoRegistryImpl(typeInfoCreator);

        TypeMappingBuilder typeMappingBuilder = new TypeMappingBuilderImpl(typeInfoRegistry);

        TypePropertyInfoRegistry typePropertyInfoRegistry = new TypePropertyInfoRegistryImpl(typeInfoRegistry);

        TypeMappingInfoRegistry mappingInfoRegistry = new TypeMappingInfoRegistryImpl(typeMappingBuilder);

        List<TypeConverterRegistry> converterRegistryList = new LinkedList<TypeConverterRegistry>();

        if (!configurationImpl.getConverterCollection().isEmpty()) {
            converterRegistryList.add(new TypeConverterRegistryImpl(configurationImpl.getConverterCollection()));
        }

        converterRegistryList.add(new TypeConverterRegistryImpl(new TypeConverterCollectionDefaultImpl()));

        TypeMappingConversionStrategyRegistry conversionStrategyRegistry = new TypeMappingConversionStrategyRegistry();
        TypeMappingConversionStrategy objectStrategy = new TypeMappingConversionStrategyObjectImpl(mappingInfoRegistry,
                typeInfoRegistry, typePropertyInfoRegistry);
        TypeMappingConversionStrategy mapStrategy = new TypeMappingConversionStrategyMapImpl(typeInfoRegistry);
        TypeMappingConversionStrategy listOfMapStrategy = new TypeMappingConversionStrategyListOfMapImpl(
                typeInfoRegistry);
        
        conversionStrategyRegistry.register(TypeMappingToStrategy.OBJECT, objectStrategy);
        conversionStrategyRegistry.register(TypeMappingToStrategy.MAP,  mapStrategy);
        conversionStrategyRegistry.register(TypeMappingToStrategy.LIST_OF_MAP,  listOfMapStrategy);
        
        TypeMappingService service = new TypeMappingServiceImpl(converterRegistryList, conversionStrategyRegistry);

        return service;
    }
}
