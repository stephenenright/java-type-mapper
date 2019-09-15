package com.stephenenright.typemapper.internal.conversion.converter;

import java.time.ZonedDateTime;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;
import com.stephenenright.typemapper.internal.util.JavaTimeUtils;

public class ZonedDateTimeToStringTypeConverter implements TypeConverter<ZonedDateTime, String> {

    public static final ZonedDateTimeToStringTypeConverter INSTANCE = new ZonedDateTimeToStringTypeConverter();

    @Override
    public String convert(TypeMappingContext<ZonedDateTime, String> context) {
        ZonedDateTime value = context.getSource();

        if (value == null) {
            return null;
        }

        return JavaTimeUtils.formatAsDateTimeOffsetISO8601(value);
    }
}
