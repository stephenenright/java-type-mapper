package com.stephenenright.typemapper.internal;

/**
 * A Strategy used to perform a type mapping from a source to a destination object. 
 *
 */
public interface TypeMappingConversionStrategy {

    public <S, D> D map(TypeMappingContextImpl<S, D> context);
}
