package com.stephenenright.typemapper;

import java.lang.reflect.Type;

import com.stephenenright.typemapper.internal.type.mapping.TypeMapping;

public interface TypeMappingContext<S, D> {

    public S getSource();

    public Class<S> getSourceType();

    public D getDestination();

    public Class<D> getDestinationType();

    public Type getGenericDestinationType();

    public boolean hasDestination();

    public TypeMapping getMapping();

    public TypeMappingService getMappingService();

    public <CS, CD> TypeMappingContext<CS, CD> createChild(CS source, Class<CD> destinationType);

    public <CS, CD> TypeMappingContext<CS, CD> createChildForObject(CS source, CD destination);

    public TypeMapperConfiguration getConfiguration();

}
