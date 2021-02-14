package com.github.stephenenright.typemapper.internal.conversion.converter;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class NullableTypeConverter implements TypeConverter<Object, Object> {

    public static TypeConverter<?, ?> INSTANCE = new NullableTypeConverter();

    @Override
    public Object convert(TypeMappingContext<Object, Object> context) {
        // TODO Auto-generated method stub
        return null;
    }
}
