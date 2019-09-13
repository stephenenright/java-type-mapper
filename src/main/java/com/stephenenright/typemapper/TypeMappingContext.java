package com.stephenenright.typemapper;

import java.lang.reflect.Type;

public interface TypeMappingContext<S, D> {

    S getSource();

    Class<S> getSourceType();
    
    D getDestination();

    Class<D> getDestinationType();
    
    Type getGenericDestinationType();

    
}
