package com.stephenenright.typemapper.internal.conversion.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import com.stephenenright.typemapper.TypeMappingContext;
import com.stephenenright.typemapper.converter.TypeConverter;

public class LongToLocalDateTimeTypeConverter implements TypeConverter<Long, LocalDateTime> {

    public static final LongToLocalDateTimeTypeConverter INSTANCE = new LongToLocalDateTimeTypeConverter();

    @Override
    public LocalDateTime convert(TypeMappingContext<Long, LocalDateTime> context) {
        Long value = context.getSource();

        if (value == null) {
            return null;
        }

        Instant instant = Instant.ofEpochMilli(value);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }
}
