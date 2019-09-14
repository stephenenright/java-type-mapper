package com.stephenenright.typemapper.internal.conversion.converter;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;

public class ObjectToStringTypeConverter implements TypeConverter<Object, String> {

    @Override
    public String convert(TypeMappingContext<Object, String> context) {
        Object value = context.getSource();

        if (value == null) {
            return null;
        }

        return value.toString();

    }
}
