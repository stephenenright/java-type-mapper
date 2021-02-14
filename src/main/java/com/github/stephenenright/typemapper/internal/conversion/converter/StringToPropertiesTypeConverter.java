package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class StringToPropertiesTypeConverter implements TypeConverter<String, Properties> {

    public static final StringToPropertiesTypeConverter INSTANCE = new StringToPropertiesTypeConverter();

    @Override
    public Properties convert(TypeMappingContext<String, Properties> context) {
        String value = context.getSource();

        if (value == null) {
            return null;
        }

        try {
            Properties props = new Properties();
            // Must use the ISO-8859-1 encoding because Properties.load(stream) expects it.
            props.load(new ByteArrayInputStream(value.getBytes(StandardCharsets.ISO_8859_1)));
            return props;
        } catch (Exception ex) {

        }

        return null;
    }

}
