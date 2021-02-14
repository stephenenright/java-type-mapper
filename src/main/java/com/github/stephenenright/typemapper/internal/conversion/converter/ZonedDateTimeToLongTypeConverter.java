package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.time.ZonedDateTime;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

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
