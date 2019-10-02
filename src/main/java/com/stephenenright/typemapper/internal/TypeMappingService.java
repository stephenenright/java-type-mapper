package com.stephenenright.typemapper.internal;

import com.stephenenright.typemapper.TypeMappingContext;

public interface TypeMappingService {
    
    public <S, D> D map(S src, Class<D> destination);

    public <S, D> D map(TypeMappingContext<S, D> context);
    
    public <S, D> D createDestination(TypeMappingContext<S, D> context);
    
    public <D> D createDestination(Class<D> type);
}
