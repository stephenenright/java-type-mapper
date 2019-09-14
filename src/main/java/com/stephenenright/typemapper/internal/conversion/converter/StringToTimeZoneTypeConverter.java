package com.stephenenright.typemapper.internal.conversion.converter;

import java.util.TimeZone;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.util.StringUtils;

public class StringToTimeZoneTypeConverter implements TypeConverter<String, TimeZone> {

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
