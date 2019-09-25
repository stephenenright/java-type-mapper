package com.stephenenright.typemapper.internal.conversion.converter;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypePredicateConverter;

public class ObjectToObjectConverter implements TypePredicateConverter<Object, Object> {

    public static ObjectToObjectConverter INSTANCE = new ObjectToObjectConverter();

    @Override
    public Object convert(TypeMappingContext<Object, Object> context) {
        return context.getDestination() != null ? context.getDestination() : context.getSource();
    }

    @Override
    public PredicateResult test(Class<?> sourceType, Class<?> destinationType) {
        return destinationType.isAssignableFrom(sourceType) ? PredicateResult.FULL : PredicateResult.NONE;
    }
}
