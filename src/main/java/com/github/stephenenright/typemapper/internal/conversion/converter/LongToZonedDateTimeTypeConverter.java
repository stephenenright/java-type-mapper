package com.github.stephenenright.typemapper.internal.conversion.converter;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import com.github.stephenenright.typemapper.TypeMappingContext;
import com.github.stephenenright.typemapper.converter.TypeConverter;

public class LongToZonedDateTimeTypeConverter implements TypeConverter<Long, ZonedDateTime> {

    public static final LongToZonedDateTimeTypeConverter INSTANCE = new LongToZonedDateTimeTypeConverter();

    @Override
    public ZonedDateTime convert(TypeMappingContext<Long, ZonedDateTime> context) {
        Long value = context.getSource();

        if (value == null) {
            return null;
        }

        Instant instant = Instant.ofEpochMilli(value);
        return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
