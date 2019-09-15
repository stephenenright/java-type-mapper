package com.stephenenright.typemapper.internal.conversion.converter;

import java.time.ZonedDateTime;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;

public class ZonedDateTimeToLongTypeConverter implements TypeConverter<ZonedDateTime, Long> {

    public static final ZonedDateTimeToLongTypeConverter INSTANCE = new ZonedDateTimeToLongTypeConverter();

    @Override
    public Long convert(TypeMappingContext<ZonedDateTime, Long> context) {
        ZonedDateTime value = context.getSource();

        if (value == null) {
            return null;
        }

        return value.toInstant().toEpochMilli();
    }
}
