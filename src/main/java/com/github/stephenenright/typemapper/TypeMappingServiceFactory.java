package com.github.stephenenright.typemapper;

import com.github.stephenenright.typemapper.internal.configuration.TypeMappingServiceConfiguer;

public abstract class TypeMappingServiceFactory {

    private TypeMappingServiceFactory() {

    }

    private static final TypeMappingService defaultMappingService = configureInternal(
            TypeMappingConfiguration.create());
    private static TypeMappingService customMappingService = null;

    public static void configure(TypeMappingConfiguration configuration) {
        customMappingService = TypeMappingServiceFactory.configureInternal(configuration);
    }

    public static TypeMappingService getMappingService() {
        if (customMappingService != null) {
            return customMappingService;
        }

        return defaultMappingService;
    }

    private static TypeMappingService configureInternal(TypeMappingConfiguration configuration) {
        return TypeMappingServiceConfiguer.configure(configuration);

    }

}
