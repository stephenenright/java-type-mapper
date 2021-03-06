package com.github.stephenenright.typemapper.internal.conversion.converter;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.internal.util.StringUtils;

@SuppressWarnings("rawtypes")
public class StringToEnumTypeConverter implements TypeConverter<String, Enum> {

    public static final StringToEnumTypeConverter INSTANCE = new StringToEnumTypeConverter();
    
    @SuppressWarnings("unchecked")
    @Override
    public Enum<?> convert(TypeMappingContext<String, Enum> context) {
        String value = context.getSource();

        if (StringUtils.isNullOrEmpty(value)) {
            return null;
        }
        try {
            return Enum.valueOf((Class) context.getDestinationType(), value);
        } catch (IllegalArgumentException ignore) {
        }

        return null;
    }

}
