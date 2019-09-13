package com.stephenenright.typemapper.internal.conversion.converter;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;

public class NullableConverter implements TypeConverter<Object, Object> {

    public static TypeConverter<?, ?> INSTANCE = new NullableConverter();

    @Override
    public Object convert(TypeMappingContext<Object, Object> context) {
        // TODO Auto-generated method stub
        return null;
    }
}
