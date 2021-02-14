package com.github.stephenenright.typemapper.converter;

public interface TypeConditionalConverter<S, D> extends TypeConverter<S, D> {

    public enum MatchResult {
        FULL, PARTIAL, NONE;
    }

    MatchResult matches(Class<?> sourceType, Class<?> destinationType);

}
