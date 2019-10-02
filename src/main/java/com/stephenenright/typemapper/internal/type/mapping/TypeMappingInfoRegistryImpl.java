package com.stephenenright.typemapper.internal.type.mapping;

import java.util.Map;

import com.stephenenright.typemapper.configuration.TypeMapperConfiguration;
import com.stephenenright.typemapper.internal.TypeMappingService;
import com.stephenenright.typemapper.internal.collection.ConcurrentReferenceHashMap;

public class TypeMappingInfoRegistryImpl implements TypeMappingInfoRegistry {

    private final Map<TypeMappingKey<?, ?>, TypeMappingInfo<?, ?>> typeMappingInfoCache = new ConcurrentReferenceHashMap<>(
            256);

    private final TypeMappingBuilder typeMappingBuilder;
    
    public TypeMappingInfoRegistryImpl(TypeMappingBuilder typeMappingBuilder) {
        this.typeMappingBuilder = typeMappingBuilder;
    }
    
    
    @SuppressWarnings("unchecked")
    @Override
    public <S, D> TypeMappingInfo<S, D> getOrRegister(S source, Class<S> sourceType, Class<D> destinationType,
            TypeMappingService mappingService, TypeMapperConfiguration configuration) {
        TypeMappingKey<S, D> key = new TypeMappingKey<S, D>(sourceType, destinationType);
        TypeMappingInfo<S, D> mappingInfo = (TypeMappingInfo<S, D>) typeMappingInfoCache.get(key);
        
        if(mappingInfo!=null) {
            return mappingInfo;
        }
        
        mappingInfo = new TypeMappingInfoImpl<S, D>(sourceType, destinationType, configuration);
        typeMappingBuilder.buildMappings(source, mappingInfo, configuration, this);
        typeMappingInfoCache.put(key, mappingInfo);
  
        return mappingInfo;
    }


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
