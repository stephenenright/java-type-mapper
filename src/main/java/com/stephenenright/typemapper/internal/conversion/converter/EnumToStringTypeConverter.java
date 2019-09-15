package com.stephenenright.typemapper.internal.conversion.converter;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;

@SuppressWarnings("rawtypes")
public class EnumToStringTypeConverter implements TypeConverter<Enum, String>, TypeValueConverter<Enum<?>, String> {

    public static final EnumToStringTypeConverter INSTANCE = new EnumToStringTypeConverter();
    
    @Override
    public String convert(TypeMappingContext<Enum, String> context) {
        return convertValue(context.getSource());
    }

    @Override
    public String convertValue(Enum<?> value) {
        if (value == null) {
            return null;
        }

        return value.name();
    }

}
