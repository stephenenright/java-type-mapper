package com.stephenenright.typemapper.internal;

import java.util.HashMap;
import java.util.Map;

public class TypeMappingConversionStrategyRegistry {

    private final static Map<TypeMappingToStrategy, TypeMappingConversionStrategy> strategyMap = new HashMap<>();

    public void register(TypeMappingToStrategy toStrategy, TypeMappingConversionStrategy conversionStrategy) {
        strategyMap.put(toStrategy, conversionStrategy);
    }

    public TypeMappingConversionStrategy get(TypeMappingToStrategy toStrategy) {
        return strategyMap.get(toStrategy);
    }
}
