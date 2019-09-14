package com.stephenenright.typemapper.internal.conversion.converter;

import java.nio.charset.Charset;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;

public class StringToCharsetTypeConveter implements TypeConverter<String, Charset> {

    @Override
    public Charset convert(TypeMappingContext<String, Charset> context) {
        String value = context.getSource();

        if (value == null) {
            return null;
        }

        try {
            return Charset.forName(value);
        } catch (Exception e) {

        }

        return null;

    }

}
