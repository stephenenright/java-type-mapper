package com.stephenenright.typemapper.internal;

import java.lang.reflect.Type;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.TypeToken;
import com.stephenenright.typemapper.internal.util.ProxyUtils;

public class TypeMappingContextImpl<S, D> implements TypeMappingContext<S, D> {

    private final S source;
    private final Class<S> sourceType;
    private final Class<D> destinationType;
    private D destination;
    private final Type genericDestinationType;
    private final TypeMappingService mappingService;

    public  TypeMappingContextImpl(S source, Class<D> destinationType, TypeMappingService mappingService) {
        this(source, ProxyUtils.unProxy(source.getClass()), null,
                destinationType,TypeToken.<D>of(destinationType).getType(), mappingService);
    }

    public TypeMappingContextImpl(S source, Class<S> sourceType, D destination, Class<D> destinationType,
            Type genericDestinationType,TypeMappingService mappingService) {
        this.source = source;
        this.sourceType = sourceType;
        this.destination = destination;
        this.destinationType = destinationType;
        this.genericDestinationType = genericDestinationType;
        this.mappingService = mappingService;
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

    public TypeMappingService getMappingService() {
        return mappingService;
    }
}
