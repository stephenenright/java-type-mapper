package com.github.stephenenright.typemapper.internal.conversion.converter;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class ObjectToStringTypeConverter implements TypeConverter<Object, String> {

    public static final ObjectToStringTypeConverter INSTANCE = new ObjectToStringTypeConverter();

    @Override
    public String convert(TypeMappingContext<Object, String> context) {
        Object value = context.getSource();

        if (value == null) {
            return null;
        }

        return value.toString();

    }
}
