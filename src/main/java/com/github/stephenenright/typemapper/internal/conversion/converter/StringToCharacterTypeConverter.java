package com.github.stephenenright.typemapper.internal.conversion.converter;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class StringToCharacterTypeConverter implements TypeConverter<String, Character> {

    public static final StringToCharacterTypeConverter INSTANCE = new StringToCharacterTypeConverter();

    @Override
    public Character convert(TypeMappingContext<String, Character> context) {
        String value = context.getSource();

        if (value == null) {
            return null;
        }

        if (value.isEmpty()) {
            return null;
        }

        if (value.length() > 1) {
            throw new IllegalArgumentException(
                    "String value to convert must have a length of 1 to covnert to a character. " + "Value: " + value
                            + ", Length: " + value.length());
        }

        return value.charAt(0);
    }
}
