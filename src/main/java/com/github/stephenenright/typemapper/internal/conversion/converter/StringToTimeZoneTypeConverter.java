package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.util.TimeZone;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;
import com.github.stephenenright.typemapper.internal.util.StringUtils;

public class StringToTimeZoneTypeConverter implements TypeConverter<String, TimeZone> {

    public static final StringToTimeZoneTypeConverter INSTANCE = new StringToTimeZoneTypeConverter();
    
    
    @Override
    public TimeZone convert(TypeMappingContext<String, TimeZone> context) {
        String value = context.getSource();

        if (StringUtils.isNullOrEmpty(value)) {
            return null;
        }

        try {
            return StringUtils.timezoneFromString(value);
        } catch (Exception e) {

        }

        return null;

    }

}
