package com.stephenenright.typemapper.internal.type.mapping;

public interface TypeMappingInfoRegistry {

    public <S, D> TypeMappingInfo<S, D> get(Class<S> sourceType, Class<D> destinationType);

}
