package com.stephenenright.typemapper.internal.conversion.converter;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConditionalConverter;

public class ObjectToObjectConverter implements TypeConditionalConverter<Object, Object> {

    public static ObjectToObjectConverter INSTANCE = new ObjectToObjectConverter();

    @Override
    public Object convert(TypeMappingContext<Object, Object> context) {
        return context.getDestination() != null ? context.getDestination() : context.getSource();
    }

    @Override
    public MatchResult matches(Class<?> sourceType, Class<?> destinationType) {
        return destinationType.isAssignableFrom(sourceType) ? MatchResult.FULL : MatchResult.NONE;
    }
}
