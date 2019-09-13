package com.stephenenright.typemapper.internal;

import java.lang.reflect.Type;

import com.stephenenright.typemapper.TypeMappingContext;

public class TypeMappingContextImpl<S, D> implements TypeMappingContext<S, D> {

    private final S source;
    private final Class<S> sourceType;
    private final Class<D> destinationType;
    private D destination;
    private final Type genericDestinationType;

    public TypeMappingContextImpl(S source, Class<S> sourceType, D destination, Class<D> destinationType,
            Type genericDestinationType) {
        this.source = source;
        this.sourceType = sourceType;
        this.destination = destination;
        this.destinationType = destinationType;
        this.genericDestinationType = genericDestinationType;
    }

    @Override
    public S getSource() {
        return source;
    }

    @Override
    public Class<S> getSourceType() {
        return sourceType;
    }

    @Override
    public D getDestination() {
        return destination;
    }

    @Override
    public Class<D> getDestinationType() {
        return destinationType;
    }

    @Override
    public Type getGenericDestinationType() {
        return genericDestinationType;
    }

}
