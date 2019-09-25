package com.stephenenright.typemapper.converter;

public interface TypeConditionalConverter<S, D> extends TypeConverter<S, D> {

    public enum MatchResult {
        FULL, PARTIAL, NONE;
    }
    
   
    MatchResult test(Class<?> sourceType, Class<?> destinationType);

}
