package com.stephenenright.typemapper.converter;

public interface TypePredicateConverter<S, D> extends TypeConverter<S, D> {

    public enum PredicateResult {
        FULL, PARTIAL, NONE;
    }
    
   
    PredicateResult test(Class<?> sourceType, Class<?> destinationType);

}
