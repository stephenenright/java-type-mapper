package com.stephenenright.typemapper.internal.type.mapping;

import java.util.Map;

import com.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;

public class TypeMappingInfoRegistryImpl implements TypeMappingInfoRegistry {

    private final Map<TypeMappingKey<?, ?>, TypeMappingInfo<?, ?>> typeMappingInfoCache = new ConcurrentReferenceHashMap<>(
            256);

    @SuppressWarnings("unchecked")
    @Override
    public <S, D> TypeMappingInfo<S, D> get(Class<S> sourceType, Class<D> destinationType) {
        TypeMappingKey<S, D> key = new TypeMappingKey<S, D>(sourceType, destinationType);

        TypeMappingInfo<S, D> typeMappingInfo = (TypeMappingInfo<S, D>) typeMappingInfoCache.get(key);

        if (typeMappingInfo != null) {
            return typeMappingInfo;
        }

        return null;

    }
}
