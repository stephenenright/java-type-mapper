package com.stephenenright.typemapper.internal.type.mapping;

import com.stephenenright.typemapper.internal.TypeMappingContextImpl;
import com.stephenenright.typemapper.internal.TypeMappingService;

public interface TypeMappingInfoRegistry {

    
    public <S, D> TypeMappingInfo<S, D> getOrRegister(S source, Class<S> sourceType, Class<D> destinationType,
            TypeMappingService mappingService, TypeMappingContextImpl<S, D> contextImpl);
    
    public <S, D> TypeMappingInfo<S, D> get(Class<S> sourceType, Class<D> destinationType);

}
