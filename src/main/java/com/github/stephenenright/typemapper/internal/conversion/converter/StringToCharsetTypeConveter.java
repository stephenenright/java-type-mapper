package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.nio.charset.Charset;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class StringToCharsetTypeConveter implements TypeConverter<String, Charset> {

    public static final StringToCharsetTypeConveter INSTANCE = new StringToCharsetTypeConveter();
    
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
