package com.github.stephenenright.typemapper.internal.type.mapping;

import com.github.stephenenright.typemapper.TypeMappingService;
import com.github.stephenenright.typemapper.internal.TypeMappingContextImpl;

public interface TypeMappingInfoRegistry {

    
    public <S, D> TypeMappingInfo<S, D> getOrRegister(S source, Class<S> sourceType, Class<D> destinationType,
                                                      TypeMappingService mappingService, TypeMappingContextImpl<S, D> contextImpl);
    
    public <S, D> TypeMappingInfo<S, D> get(Class<S> sourceType, Class<D> destinationType);

}
