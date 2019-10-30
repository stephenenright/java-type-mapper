package com.stephenenright.typemapper.internal;

import java.util.HashMap;
import java.util.Map;

import com.stephenenright.typemapper.TypeInfoRegistry;

/**
 * Opinionated TypeMappingConversionStrategy that converts a source object to a
 * map of maps
 */
public class TypeMappingConversionStrategyMapImpl extends TypeMappingConversionStrategyMapBase
        implements TypeMappingConversionStrategy {

    public TypeMappingConversionStrategyMapImpl(TypeInfoRegistry typeInfoRegistry) {
        super(typeInfoRegistry);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S, D> D map(TypeMappingContextImpl<S, D> context) {
        ConversionContextImpl conversionContext = new ConversionContextImpl(context);
        S sourceValue = context.getSource();

        if (sourceValue == null) {
            return null;
        }

        mapFromSourceRoot(sourceValue, conversionContext);
        return (D) conversionContext.getDestinationRoot();
    }

    private static class ConversionContextImpl extends ConversionContext<Map<String, Object>> {

        public ConversionContextImpl(TypeMappingContextImpl<?, ?> mappingContext) {
            super(mappingContext);
            destinationRoot = new HashMap<>();
        }

        @Override
        protected void setRootObjectPropertyValue(String propertyName, Object value) {
            destinationRoot.put(propertyName, value);

        }

        @Override
        public Object getBuildingDestination() {
            return destinationRoot;
        }
    }
}
