package com.github.stephenenright.typemapper.internal.conversion.converter;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class NumberToCharacterTypeConverter implements TypeConverter<Number, Character> {

    public static final NumberToCharacterTypeConverter INSTANCE = new NumberToCharacterTypeConverter();

    @Override
    public Character convert(TypeMappingContext<Number, Character> context) {
        Number value = context.getSource();
        if (value == null) {
            return null;
        }

        return (char) value.shortValue();
    }
}
